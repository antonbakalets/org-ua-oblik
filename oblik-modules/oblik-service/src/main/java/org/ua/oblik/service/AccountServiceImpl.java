package org.ua.oblik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.EntitiesFactory;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.mapping.AccountMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Account service implementation.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private EntitiesFactory entitiesFactory;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountVO getAccount(Integer accountId) {
        return accountDao.findById(accountId)
                .map(this::convert)
                .orElseThrow((() -> new UnsupportedOperationException(
                        "Exception handling not implemented yet: throw new NotFoundException(message).")));
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
        return accountDao.calculateDefaultTotal();
    }

    private void insert(AccountVO avo) {
        LOGGER.debug("Saving new acсount, name: {}.", avo.getName());
        final Currency currency = currencyDao.findById(avo.getCurrencyId()).get();
        final Account account = entitiesFactory.createAccountEntity();
        account.setCurrency(currency);
        account.setKind(accountMapper.convert(avo.getType()));
        account.setShortName(avo.getName());
        // On creation account amount in zero
        account.setTotal(BigDecimal.ZERO);
        accountDao.save(account);
        avo.setAccountId(account.getId());
    }

    private void update(AccountVO avo) {
        LOGGER.debug("Updating acсount, name: {}", avo.getName());
        final Currency currency = currencyDao.findById(avo.getCurrencyId()).get();
        final Account account = accountDao.findById(avo.getAccountId()).get();
        account.setCurrency(currency);
        account.setKind(accountMapper.convert(avo.getType()));
        account.setShortName(avo.getName());
        account.setTotal(avo.getAmount());
    }

    @Override
    public List<AccountVO> getAccounts(AccountCriteria criteria) {
        AccountFilter filter = new AccountFilter();
        filter.setCriteria(criteria);
        return filter.filter(convert(accountDao.findAll()));
    }

    @Override
    @Transactional
    public void delete(Integer accountId) throws NotFoundException, BusinessConstraintException {
        if (accountDao.existsById(accountId)) {
            if (accountDao.isUsed(accountId)) {
                throw new BusinessConstraintException("Account is used by a transaction.");
            } else {
                accountDao.deleteById(accountId);
            }
        } else {
            throw new NotFoundException("Could not find account.");
        }
    }

    @Override
    public boolean isNameExists(String name) {
        return accountDao.existsByShortName(name);
    }

    private boolean isNoTransactions(Integer accountId) {
        return accountId != null && !accountDao.isUsed(accountId);
    }

    private AccountVO convert(Account model) {
        AccountVO result = accountMapper.convert(model);
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
}
