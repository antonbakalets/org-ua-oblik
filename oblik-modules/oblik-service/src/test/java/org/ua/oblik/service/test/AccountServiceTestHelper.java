package org.ua.oblik.service.test;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.AccountServiceTest;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
@Component
public class AccountServiceTestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceTestHelper.class);

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CurrencyServiceTestHelper currencyServiceTestHelper;
    
    private Map<DefinedAccount, AccountVO> accounts = new EnumMap<>(DefinedAccount.class);
    
    public AccountVO getDefinedAccount(DefinedAccount da) {
        return accounts.get(da);
    }
    
    public void saveDefinedAccounts() {
        for (DefinedAccount da : DefinedAccount.values()) {
            accounts.put(da, createAndSaveAccount(da));
        }
    }
    
    private AccountVO createAndSaveAccount(DefinedAccount definedAccount) {
        AccountVO account = createAccount(definedAccount);
        try {
            accountService.save(account);
        } catch (Exception e) {
            LOGGER.debug("Ignoring.", e);
        }
        return account;
    }

    private AccountVO createAccount(DefinedAccount definedAccount) {
        final CurrencyVO currencyVO = currencyServiceTestHelper.getDefinedCurrency(definedAccount.getCurrency());
        final AccountVO result  = new AccountVO();
        result.setAmount(BigDecimal.ZERO);
        result.setCurrencyId(currencyVO.getCurrencyId());
        result.setCurrencySymbol(currencyVO.getSymbol());
        result.setName(definedAccount.getAccountName());
        result.setType(definedAccount.getAccountType());
        return result;
    }
    
    public Integer accountId(DefinedAccount definedAccount) {
        return accounts.get(definedAccount).getAccountId();
    }
    
    public Integer currencyId(DefinedAccount definedAccount) {
        return accounts.get(definedAccount).getCurrencyId();
    }
    
    public BigDecimal rate(DefinedAccount definedAccount) {
        return currencyServiceTestHelper.getRate(accounts.get(definedAccount).getCurrencyId());
    }
    
    public AccountVOType type(DefinedAccount definedAccount) {
        return accounts.get(definedAccount).getType();
    }

    public BigDecimal getAmount(DefinedAccount definedAccount) {
        return accountService.getAccount(accountId(definedAccount)).getAmount();
    }
}
