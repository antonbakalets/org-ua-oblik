package org.ua.oblik.service;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;
import org.ua.oblik.service.test.DefinedAccount;
import org.ua.oblik.service.test.DefinedCurrency;

import java.math.BigDecimal;

abstract class BaseTransactionServiceTest extends BaseServiceTest {

    @Autowired
    protected CurrencyServiceTestHelper cH;

    @Autowired
    protected AccountServiceTestHelper aH;

    @Autowired
    protected TransactionService transactionService;

    @Autowired
    protected TotalService totalService;

    protected BigDecimal defaultCurrencyTotal;

    public void defaultBefore() {
        defaultCurrencyTotal = totalService.getDefaultCurrencyTotal();
    }

    public void defaultAfter(BigDecimal after) {
        defaultCurrencyTotal = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals(defaultCurrencyTotal + " != " + after,
                defaultCurrencyTotal.compareTo(after), 0);
    }

    public BigDecimal totalByCurrency(DefinedCurrency definedCurrency) {
        return totalService.getCurrencyTotal(cH.currencyId(definedCurrency));
    }

    public BigDecimal totalByCurrency(DefinedAccount definedAccount) {
        Assert.assertEquals(aH.type(definedAccount), AccountVOType.ASSETS);
        return totalService.getCurrencyTotal(aH.currencyId(definedAccount));
    }

    public BigDecimal totalByAccount(DefinedAccount definedAccount) {
        Assert.assertEquals(aH.type(definedAccount), AccountVOType.ASSETS);
        return aH.getAmount(definedAccount);
    }
}
