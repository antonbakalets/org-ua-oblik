package org.ua.oblik.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;
import org.ua.oblik.service.test.DefinedAccount;
import org.ua.oblik.service.test.DefinedCurrency;

/**
 *
 * @author Anton Bakalets
 */
public class AccountFilterTest extends BaseServiceCheckConfig {
    
    private final List<AccountVO> accounts = new ArrayList<>();
    
    @Autowired
    private AccountServiceTestHelper aH;
    
    @Autowired
    private CurrencyServiceTestHelper cH;
    
    @Before
    public void setUp() {
        
        aH.getDefinedAccount(DefinedAccount.USD_CARD);
        accounts.add(aH.getDefinedAccount(DefinedAccount.UGH_CASH));
        accounts.add(aH.getDefinedAccount(DefinedAccount.USD_CARD));
        accounts.add(aH.getDefinedAccount(DefinedAccount.EXPENSE_IN_DOLLAR));
        accounts.add(aH.getDefinedAccount(DefinedAccount.EXPENSE_IN_EURO));
        accounts.add(aH.getDefinedAccount(DefinedAccount.INCOME_FOUND_USD));
        accounts.add(aH.getDefinedAccount(DefinedAccount.INCOME_SALARY));
    }
    
    @Test
    public void testEmptyCriteria() {
        AccountFilter filter = new AccountFilter();
        final List<AccountVO> result = filter.filter(accounts);
        Assert.assertEquals(result.size(), 6);
    }
    
    @Test
    public void testAssetsCriteria() {
        AccountFilter filter = new AccountFilter();
        filter.setCriteria(AccountCriteria.ASSETS_CRITERIA);
        final List<AccountVO> result = filter.filter(accounts);
        Assert.assertEquals(result.size(), 2);
    }
    
    @Test
    public void testExpenseCriteria() {
        AccountFilter filter = new AccountFilter();
        filter.setCriteria(AccountCriteria.EXPENSE_CRITERIA);
        final List<AccountVO> result = filter.filter(accounts);
        Assert.assertEquals(result.size(), 2);
    }
    
    @Test
    public void testIncomeCriteria() {
        AccountFilter filter = new AccountFilter();
        filter.setCriteria(AccountCriteria.INCOME_CRITERIA);
        final List<AccountVO> result = filter.filter(accounts);
        Assert.assertEquals(result.size(), 2);
    }
    
    @Test
    public void testIncomeUsdCriteria() {
        AccountFilter filter = new AccountFilter();
        filter.setCriteria(new AccountCriteria.Builder()
                .setType(AccountVOType.INCOME)
                .setCurrencyId(cH.getDefinedCurrency(DefinedCurrency.USD).getCurrencyId())
                .build());
        final List<AccountVO> result = filter.filter(accounts);
        Assert.assertEquals(result.size(), 1);
    }
    
    @Test
    public void testExspenseUghCriteria() {
        AccountFilter filter = new AccountFilter();
        filter.setCriteria(new AccountCriteria.Builder()
                .setType(AccountVOType.EXPENSE)
                .setCurrencyId(cH.getDefinedCurrency(DefinedCurrency.UGH).getCurrencyId())
                .build());
        final List<AccountVO> result = filter.filter(accounts);
        Assert.assertEquals(result.size(), 0);
    }
}
