package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.AccountEntity;
import org.ua.oblik.domain.model.CurrencyEntity;
import org.ua.oblik.domain.model.EntitiesFactory;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private EntitiesFactory entitiesFactory;

    @Override
    public AccountVO getAccount(Integer accountId) {
        final AccountEntity selected = accountDao.select(accountId);
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
            BigDecimal toAdd = avo.getAmmount().multiply(cvo.getRate());
            result = result.add(toAdd);
        }
        return result;
    }

    private void insert(AccountVO avo) {
        LOGGER.debug("Saving new acoount, name: " + avo.getName());
        final CurrencyEntity currency = currencyDao.select(avo.getCurrencyId());
        final AccountEntity account = entitiesFactory.createAccountEntity();
        account.setCurrency(currency);
        account.setKind(AccountTypeConverter.convert(avo.getType()));
        account.setShortName(avo.getName());
        // On creation account ammount in zero
        account.setTotal(BigDecimal.ZERO);
        accountDao.insert(account);
        avo.setAccountId(account.getId());
    }

    private void update(AccountVO avo) {
        LOGGER.debug("Updating acoount, name: " + avo.getName());
        final CurrencyEntity currency = currencyDao.select(avo.getCurrencyId());
        final AccountEntity account = accountDao.select(avo.getAccountId());
        account.setCurrency(currency);
        account.setKind(AccountTypeConverter.convert(avo.getType()));
        account.setShortName(avo.getName());
        account.setTotal(avo.getAmmount());
        accountDao.update(account);
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
        HashMap<CurrencyVO, BigDecimal> result = new HashMap<CurrencyVO, BigDecimal>();

        for (CurrencyVO cvo : listCur) {
            BigDecimal toRes = BigDecimal.ZERO;
            for (AccountVO avo : listAcc) {
                if (avo.getCurrencyId().equals(cvo.getCurrencyId())) {
                    toRes = toRes.add(avo.getAmmount());
                }
            }
            result.put(cvo, toRes);
        }
        return result;
    }

    @Transactional
    @Override
    public void delete(Integer accountId) {
        AccountEntity account = accountDao.select(accountId);
        accountDao.delete(account);
    }

    @Override
    public boolean isNameExists(String name) {
        return accountDao.isNameExists(name);
    }

    private boolean hasTransactions(Integer accountId) {
        return accountId != null && accountDao.isUsed(accountId);
    }

    private AccountVO convert(AccountEntity model) {
        AccountVO result = new AccountVO();
        result.setAccountId(model.getId());
        result.setCurrencyId(model.getCurrency().getId());
        result.setCurrencySymbol(model.getCurrency().getSymbol());
        result.setName(model.getShortName());
        result.setAmmount(model.getTotal());
        result.setType(AccountTypeConverter.convert(model.getKind()));
        result.setRemovable(hasTransactions(model.getId()));
        return result;
    }

    private List<AccountVO> convert(List<? extends AccountEntity> modelList) {
        List<AccountVO> result = new ArrayList<>(modelList.size());
        for (AccountEntity model : modelList) {
            result.add(convert(model));
        }
        return result;
    }
}
