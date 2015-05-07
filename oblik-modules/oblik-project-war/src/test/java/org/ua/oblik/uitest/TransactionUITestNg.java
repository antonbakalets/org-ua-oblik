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

import static org.testng.Assert.assertTrue;

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
        currencyHelper = new CurrencyUITestHelper(this);
        accountHelper.deleteAllAccounts();
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
    public static Iterator<Object[]> transactionProvider() {
        Set<Object[]> result = new HashSet<>();
        result.add(new Object[]{TransactionType.INCOME, INCOME1, BigDecimal.TEN, new Date(), ASSETS1, null});
        result.add(new Object[]{TransactionType.TRANSFER, ASSETS1, BigDecimal.TEN, new Date(), ASSETS2, BigDecimal.TEN});
        result.add(new Object[]{TransactionType.EXPENSE, ASSETS2, BigDecimal.TEN, new Date(), EXPENSE1, null});
        return result.iterator();
    }

    @DataProvider(name = "incorrectTransactionProvider")
    public static Iterator<Object[]> incorrectTransactionProvider() {
        Set<Object[]> result = new HashSet<>();
        result.add(new Object[]{TransactionType.INCOME, null, null, null, null, null,
                new String[]{"firstAmount.errors", "firstAmount.errors", "date.errors", "secondAccount.errors"}});
        result.add(new Object[]{TransactionType.TRANSFER, null, null, null, null, null,
                new String[]{"firstAmount.errors", "firstAmount.errors", "date.errors", "secondAccount.errors"}});
        result.add(new Object[]{TransactionType.EXPENSE, null, null, null, null, null,
                new String[]{"firstAmount.errors", "firstAmount.errors", "date.errors", "secondAccount.errors"}});
        result.add(new Object[]{TransactionType.TRANSFER, ASSETS1, BigDecimal.TEN, new Date(), ASSETS1, BigDecimal.TEN,
                new String[]{"secondAccount.errors"}});
        result.add(new Object[]{TransactionType.INCOME, INCOME2, BigDecimal.TEN, new Date(), ASSETS1, BigDecimal.TEN,
                new String[]{"secondAccount.errors"}});
        result.add(new Object[]{TransactionType.EXPENSE, ASSETS1, BigDecimal.TEN, new Date(), EXPENSE2, BigDecimal.TEN,
                new String[]{"secondAccount.errors"}});
        return result.iterator();
    }

    @Test(dataProvider = "incorrectTransactionProvider")
    public void makeIncorrectTransaction(TransactionType type, String firstAccountName, BigDecimal firstAmount, Date txDate,
                                String secondAccountName, BigDecimal secondAmount, String[] errorIds) {
        WebElement section = makeTransaction(type, firstAccountName, firstAmount, txDate, secondAccountName, secondAmount);
        for (String errorId : errorIds) {
            try {
                assertTrue(section.findElement(By.id("firstAmount.errors")).isDisplayed(),
                        "Can't find elem by id: '" + errorId + "'");
            } catch (org.openqa.selenium.StaleElementReferenceException sere) {
                LOGGER.info("Can't find elem by id: '{}'", errorId);
                LOGGER.info(sere.getMessage());
            }
        }
        section.findElement(By.id("action-cancel")).click();
        driverWait.until(progressFinished());
    }

    @Test(dataProvider = "transactionProvider")
    public void makeCorrectTransaction(TransactionType type, String firstAccountName, BigDecimal firstAmount, Date txDate,
                                       String secondAccountName, BigDecimal secondAmount) {

        WebElement section = makeTransaction(type, firstAccountName, firstAmount, txDate, secondAccountName, secondAmount);
    }

    private WebElement makeTransaction(TransactionType type, String firstAccountName, BigDecimal firstAmount, Date txDate, String secondAccountName, BigDecimal secondAmount) {
        clickTransactionType(type);

        WebElement section = driver.findElement(By.id("form-actions"));

        if (firstAccountName != null) {
            Select firstSelect = new Select(section.findElement(By.id("account-from")));
            firstSelect.selectByVisibleText(firstAccountName);
        }

        if (firstAmount != null) {
            WebElement firstAmountElem = section.findElement(By.id("firstAmount"));
            String firstAmountFormatted = DECIMAL_FORMAT.format(firstAmount);
            LOGGER.debug("First account amount: {}", firstAmountFormatted);
            firstAmountElem.sendKeys(firstAmountFormatted);
        }

        WebElement txDateElem = section.findElement(By.id("date"));
        if (txDate != null) {
            txDateElem.sendKeys("04.05.2015"); // TODO format txDate
        } else {
            txDateElem.clear();
        }

        if (secondAccountName != null) {
            Select secondSelect = new Select(section.findElement(By.id("account-to")));
            secondSelect.selectByVisibleText(secondAccountName);
        }

        if (secondAmount != null) {
            WebElement secondAmountElem = section.findElement(By.id("secondAmount"));
            String secondAmountFormatted = DECIMAL_FORMAT.format(secondAmount);
            LOGGER.debug("First account amount: {}", secondAmountFormatted);
            secondAmountElem.sendKeys(secondAmountFormatted);
        }

        String note = txDate + ", " + type + ", " +
                firstAccountName + ", " + firstAmount + ", " +
                secondAccountName + ", " + secondAmount;
        WebElement noteElem = section.findElement(By.id("note"));
        noteElem.sendKeys(note);

        section.findElement(By.id("action-button")).click();
        driverWait.until(progressFinished());
        return driver.findElement(By.id("form-actions"));
    }

    //@AfterClass
    public void tearDownClass() {
        accountHelper.deleteAllAccounts();
        currencyHelper.deleteAllCurrencies();
    }
}
