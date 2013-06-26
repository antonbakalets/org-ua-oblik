package org.ua.oblik.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;

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

    @Override
    public AccountVO getAccount(Integer accountId) {
        return convert(accountDao.select(accountId));
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

    private void insert(AccountVO avo) {
        LOGGER.debug("Saving new acoount, name: " + avo.getName());
        final Currency currency = currencyDao.select(avo.getCurrencyId());
        final Account account = new Account();
        account.setCurrency(currency);
        account.setKind(convertType(avo.getType())); // TODO
        account.setShortName(avo.getName());
        account.setTotal(avo.getAmmount());
        accountDao.insert(account);
        avo.setAccountId(account.getAccoId());
    }

    private void update(AccountVO avo) {
        LOGGER.debug("Updating acoount, name: " + avo.getName());
        final Currency currency = currencyDao.select(avo.getCurrencyId());
        final Account account = accountDao.select(avo.getAccountId());
        //account.setCurrency(currency);
        //account.setKind(); // TODO
        account.setShortName(avo.getName());
        //account.setTotal(BigDecimal.ZERO);
        accountDao.update(account);
    }

    @Override
    public List<AccountVO> getExpenseAccounts() {
        return convert(accountDao.selectByKind(AccountKind.EXPENSE));
    }

    @Override
    public List<AccountVO> getIncomeAccounts() {
        return convert(accountDao.selectByKind(AccountKind.INCOME));
    }

    @Override
    public List<AccountVO> getAssetsAccounts() {
        return convert(accountDao.selectByKind(AccountKind.ASSETS));
    }

    private static AccountVO convert(Account model) {
        AccountVO result = new AccountVO();
        result.setAccountId(model.getId());
        result.setCurrencyId(model.getCurrency().getId());
        result.setCurrencySymbol(model.getCurrency().getSymbol());
        result.setName(model.getShortName());
        result.setAmmount(model.getTotal());
        result.setType(convertType(model.getKind()));
        return result;
    }

    private static List<AccountVO> convert(List<Account> modelList) {
        List<AccountVO> result = new ArrayList<AccountVO>(modelList.size());
        for (Account model : modelList) {
            result.add(convert(model));
        }
        return result;
    }

    private static AccountVOType convertType(AccountKind kind) {
        switch (kind) {
            case ASSETS:
                return AccountVOType.ASSETS;
            case EXPENSE:
                return AccountVOType.EXPENSE;
            case INCOME:
                return AccountVOType.INCOME;
        }
        throw new IllegalArgumentException("Unnexisting account type.");
    }

    private static AccountKind convertType(AccountVOType type) {
        switch (type) {
            case ASSETS:
                return AccountKind.ASSETS;
            case EXPENSE:
                return AccountKind.EXPENSE;
            case INCOME:
                return AccountKind.INCOME;
        }
        throw new IllegalArgumentException("Unnexisting account type.");
    }
}
