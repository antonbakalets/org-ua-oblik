package org.ua.oblik.uitest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
        accountHelper.addAccount(AccountVOType.ASSETS, ASSETS1, CURRENCY1);
        accountHelper.addAccount(AccountVOType.ASSETS, ASSETS2, CURRENCY2);
        accountHelper.addAccount(AccountVOType.EXPENSE, EXPENSE1, CURRENCY1);
        accountHelper.addAccount(AccountVOType.EXPENSE, EXPENSE2, CURRENCY2);
        accountHelper.addAccount(AccountVOType.INCOME, INCOME1, CURRENCY1);
        accountHelper.addAccount(AccountVOType.INCOME, INCOME2, CURRENCY2);
    }

    @DataProvider(name = "transactionProvider")
    public static Iterator<Object[]> accountTypeProvider() {
        Set<Object[]> result = new HashSet<>();
        result.add(new Object[]{TransactionType.INCOME, INCOME1, BigDecimal.TEN, new Date(), ASSETS1, null});
        result.add(new Object[]{TransactionType.TRANSFER, ASSETS1, BigDecimal.TEN, new Date(), ASSETS2, BigDecimal.TEN});
        result.add(new Object[]{TransactionType.EXPENSE, ASSETS2, BigDecimal.TEN, new Date(), EXPENSE1, null});
        return result.iterator();
    }

    @Test(dataProvider = "transactionProvider")
    public void makeTransaction(TransactionType type, String firstAccountName, BigDecimal firstAmount, Date txDate,
                                                      String secondAccountName, BigDecimal secondAmount) {

        clickTransactionType(type);

        WebElement section = driver.findElement(By.id("form-actions"));

        Select firstSelect = new Select(section.findElement(By.id("account-from")));
        firstSelect.selectByVisibleText(firstAccountName);

        WebElement firstAmountElem = section.findElement(By.id("firstAmount"));
        firstAmountElem.sendKeys(DECIMAL_FORMAT.format(firstAmount));

        WebElement txDateElem = section.findElement(By.id("date"));
        txDateElem.sendKeys("04.05.2015"); // TODO format txDate

        Select secondSelect = new Select(section.findElement(By.id("account-to")));
        secondSelect.selectByVisibleText(secondAccountName);

        if (secondAmount != null) {
            WebElement secondAmountElem = section.findElement(By.id("secondAmount"));
            secondAmountElem.sendKeys(DECIMAL_FORMAT.format(secondAmount));
        }

        String note = txDate + ", " + type + ", " +
                firstAccountName + ", " + firstAmount + ", " +
                secondAccountName + ", " + secondAmount;
        WebElement noteElem = section.findElement(By.id("note"));
        noteElem.sendKeys(note);

        section.findElement(By.id("action-button")).click();
    }

    //@AfterClass
    public void tearDownClass() {
        accountHelper.deleteAllAccounts();
        currencyHelper.deleteAllCurrencies();
    }
}
