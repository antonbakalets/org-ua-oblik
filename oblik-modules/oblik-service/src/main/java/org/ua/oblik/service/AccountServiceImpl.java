package org.ua.oblik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.EntitiesFactory;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.CurrencyVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Anton Bakalets
 */
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountDao accountDao;
    private CurrencyDao currencyDao;
    private CurrencyService currencyService;
    private EntitiesFactory entitiesFactory;

    @Override
    public AccountVO getAccount(Integer accountId) {
        final Account selected = accountDao.select(accountId);
        if (selected == null) {
            final String message = "No account found with id " + accountId;
            LOGGER.error(message);
            throw new EntityNotFoundException(message);
        } else {
            return convert(selected);
        }
    }

    @Override
    @Transactional
    public void save(AccountVO avo) {
        if (avo.getAccountId() == null) {
            insert(avo);
        } else {
            update(avo);
        }
    }

    @Override
    public BigDecimal totalAssets() {
        BigDecimal result = BigDecimal.ZERO;
        List<AccountVO> list = getAssetsAccounts();
        for (AccountVO avo : list) {
            Integer currencyId = avo.getCurrencyId();
            CurrencyVO cvo = currencyService.getCurrency(currencyId);
            BigDecimal toAdd = avo.getAmount().multiply(cvo.getRate());
            result = result.add(toAdd);
        }
        return result;
    }

    private void insert(AccountVO avo) {
        LOGGER.debug("Saving new acoount, name: " + avo.getName());
        final Currency currency = currencyDao.select(avo.getCurrencyId());
        final Account account = entitiesFactory.createAccountEntity();
        account.setCurrency(currency);
        account.setKind(AccountTypeConverter.convert(avo.getType()));
        account.setShortName(avo.getName());
        // On creation account amount in zero
        account.setTotal(BigDecimal.ZERO);
        accountDao.insert(account);
        avo.setAccountId(account.getId());
    }

    private void update(AccountVO avo) {
        LOGGER.debug("Updating acoount, name: " + avo.getName());
        final Currency currency = currencyDao.select(avo.getCurrencyId());
        final Account account = accountDao.select(avo.getAccountId());
        account.setCurrency(currency);
        account.setKind(AccountTypeConverter.convert(avo.getType()));
        account.setShortName(avo.getName());
        account.setTotal(avo.getAmount());
    }

    private List<AccountVO> getAssetsAccounts() {
        return convert(accountDao.selectByKind(AccountKind.ASSETS));
    }
    
    @Override
    public List<AccountVO> getAccounts(AccountCriteria criteria) {
        AccountFilter filter = new AccountFilter();
        filter.setCriteria(criteria);
        return filter.filter(convert(accountDao.selectAll()));
    }

    @Override
    public Map<CurrencyVO, BigDecimal> totalAssetsByCurrency() {
        List<AccountVO> listAcc = getAssetsAccounts();
        List<CurrencyVO> listCur = currencyService.getCurrencies();
        HashMap<CurrencyVO, BigDecimal> result = new HashMap<>();

        for (CurrencyVO cvo : listCur) {
            BigDecimal toRes = BigDecimal.ZERO;
            for (AccountVO avo : listAcc) {
                if (avo.getCurrencyId().equals(cvo.getCurrencyId())) {
                    toRes = toRes.add(avo.getAmount());
                }
            }
            result.put(cvo, toRes);
        }
        return result;
    }

    @Override
    @Transactional
    public void delete(Integer accountId) {
        accountDao.delete(accountId);
    }

    @Override
    public boolean isNameExists(String name) {
        return accountDao.isNameExists(name);
    }

    private boolean isNoTransactions(Integer accountId) {
        return accountId != null && !accountDao.isUsed(accountId);
    }

    private AccountVO convert(Account model) {
        AccountVO result = new AccountVO();
        result.setAccountId(model.getId());
        result.setCurrencyId(model.getCurrency().getId());
        result.setCurrencySymbol(model.getCurrency().getSymbol());
        result.setName(model.getShortName());
        result.setAmount(model.getTotal());
        result.setType(AccountTypeConverter.convert(model.getKind()));
        result.setRemovable(isNoTransactions(model.getId()));
        return result;
    }

    private List<AccountVO> convert(List<? extends Account> modelList) {
        List<AccountVO> result = new ArrayList<>(modelList.size());
        for (Account model : modelList) {
            result.add(convert(model));
        }
        return result;
    }

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Autowired
    public void setEntitiesFactory(EntitiesFactory entitiesFactory) {
        this.entitiesFactory = entitiesFactory;
    }
}
