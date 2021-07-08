package org.ua.oblik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.dao.AccountRepository;
import org.ua.oblik.domain.dao.CurrencyRepository;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.AccountKind;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
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
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountVO getAccount(Integer accountId) {
        return accountRepository.findById(accountId)
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
        return accountRepository.calculateDefaultTotal();
    }

    private void insert(AccountVO avo) {
        LOGGER.debug("Saving new acсount, name: {}.", avo.getName());
        final Currency currency = currencyRepository.findById(avo.getCurrencyId()).get();
        final Account account = new Account();
        account.setCurrency(currency);
        account.setKind(accountMapper.convert(avo.getType()));
        account.setShortName(avo.getName());
        // On creation account amount in zero
        account.setTotal(BigDecimal.ZERO);
        accountRepository.save(account);
        avo.setAccountId(account.getAccoId());
    }

    private void update(AccountVO avo) {
        LOGGER.debug("Updating acсount, name: {}", avo.getName());
        final Currency currency = currencyRepository.findById(avo.getCurrencyId()).get();
        final Account account = accountRepository.findById(avo.getAccountId()).get();
        account.setCurrency(currency);
        account.setKind(accountMapper.convert(avo.getType()));
        account.setShortName(avo.getName());
        account.setTotal(avo.getAmount());
    }

    @Override
    public List<AccountVO> getAccounts(AccountVOType accountType) {
        AccountKind kind = accountMapper.convert(accountType);
        return convert(accountRepository.findByKind(kind));
    }

    @Override
    @Transactional
    public void delete(Integer accountId) throws NotFoundException, BusinessConstraintException {
        if (accountRepository.existsById(accountId)) {
            if (accountRepository.isInUse(accountId)) {
                throw new BusinessConstraintException("Account is used by a transaction.");
            } else {
                accountRepository.deleteById(accountId);
            }
        } else {
            throw new NotFoundException("Could not find account.");
        }
    }

    @Override
    public boolean isNameExists(String name) {
        return accountRepository.existsByShortName(name);
    }

    private boolean isNoTransactions(Integer accountId) {
        return !accountRepository.isInUse(accountId);
    }

    private AccountVO convert(Account model) {
        AccountVO result = accountMapper.convert(model);
        result.setRemovable(isNoTransactions(model.getAccoId()));
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
