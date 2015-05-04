package org.ua.oblik.uitest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.TransactionType;

/**
 *
 */
public class TransactionUITestNg extends AbstractUITestNg {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionUITestNg.class);

    private AccountUITestHelper accountHelper;
    private CurrencyUITestHelper currencyHelper;

    @BeforeClass
    @Parameters({ "username", "password" })
    public void setUpClass(String username, String password) {
        login(username, password);
        accountHelper = new AccountUITestHelper(this);
        accountHelper.deleteAllAccounts();
        currencyHelper = new CurrencyUITestHelper(this);
        currencyHelper.deleteAllCurrencies();
        currencyHelper.addDefaultCurrency(CURRENCY1);
        currencyHelper.addCurrency(CURRENCY2, "2");
        currencyHelper.addCurrency(CURRENCY3, "3");
        accountHelper.addAccount(AccountVOType.ASSETS, "assets1", CURRENCY1);
    }

    @Test(dataProvider = "transactionTypeProvider")
    public void makeTransaction(TransactionType type) {

    }

    @AfterClass
    public void tearDownClass() {
        accountHelper.deleteAllAccounts();
        currencyHelper.deleteAllCurrencies();
    }
}
