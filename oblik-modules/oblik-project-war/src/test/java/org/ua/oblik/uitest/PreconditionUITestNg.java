package org.ua.oblik.uitest;

import static org.testng.Assert.assertEquals;
import static org.ua.oblik.uitest.AccountUITestHelper.getAddButtonName;
import static org.ua.oblik.uitest.AccountUITestHelper.getAddLiName;

import java.math.BigDecimal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.TransactionType;

/**
 *
 */
public class PreconditionUITestNg extends AbstractUITestNg {

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
        accountHelper.addAccount(AccountVOType.ASSETS, ASSETS1, CURRENCY1);
        accountHelper.addAccount(AccountVOType.EXPENSE, EXPENSE1, CURRENCY1);
        accountHelper.addAccount(AccountVOType.INCOME, INCOME1, CURRENCY1);
    }

    @Test
    public void testNoMoney() {
        BigDecimal zeroDecimal = BigDecimal.ZERO.multiply(new BigDecimal("1.00"));
        assertEquals(getDefaultTotal(), zeroDecimal, "No money at start.");
    }

    @Test(dataProvider = "accountTypeProvider")
    public void testCurrencyList(AccountVOType type) {
        WebElement liAccountAdd = driver.findElement(getAddLiName(type));
        liAccountAdd.findElement(getAddButtonName(type)).click();
        driverWait.until(elementFinishedResizing(liAccountAdd));

        Select currencySelect = new Select(liAccountAdd.findElement(By.id("currency")));
        WebElement firstSelectedOption = currencySelect.getFirstSelectedOption();
        assertEquals(firstSelectedOption.getText(), currencyHelper.getDefaultCurrency(), "Default currency is preselected.");
        assertSameOptions(currencySelect, currencyHelper.getCurrencies().keySet());
    }

    @Test(dataProvider = "transactionAndAccountTypeProvider")
    public void testAccountList(TransactionType type, AccountVOType first, AccountVOType second) {
        clickTransactionType(type);
        Select firstSelect = new Select(driver.findElement(By.id("account-from")));
        Select secondSelect = new Select(driver.findElement(By.id("account-to")));
        assertSameOptions(firstSelect, accountHelper.getAccountsWithPlaceHolder(first).keySet());
        assertSameOptions(secondSelect, accountHelper.getAccountsWithPlaceHolder(second).keySet());
    }

    @AfterClass
    public void tearDownClass() {
        accountHelper.deleteAllAccounts();
        currencyHelper.deleteAllCurrencies();
    }
}
