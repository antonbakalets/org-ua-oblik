package org.ua.oblik.uitest;

import static org.ua.oblik.uitest.AccountUITestHelper.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.ua.oblik.service.beans.AccountVOType;

/**
 *
 */
public class AccountUITestNg extends AbstractUITestNg {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountUITestNg.class);

    public static final String ACCOUNT1 = "account1";
    public static final String ACCOUNT2 = "account2";
    public static final String CURRENCY1 = "грн";
    public static final String CURRENCY2 = "usd";
    public static final String CURRENCY3 = "euro";
    private static final String TOOLONG = "more than 100 123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

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
    }

    @DataProvider(name = "typeProvider")
    public static Iterator<Object[]> typeProvider() {
        Set<Object[]> result = new HashSet<>();
        for (AccountVOType type : AccountVOType.values()) {
            result.add(new Object[]{type});
        }
        return result.iterator();
    }

    @Test(dataProvider = "typeProvider")
    public void testTryToAddCurrency(AccountVOType type) {
        WebElement section = driver.findElement(sectionByAccountType(type));
        WebElement liAccountAdd = driver.findElement(getAddLiName(type));
        int beforeLi = section.findElements(By.tagName("li")).size();
        liAccountAdd.findElement(getAddButtonName(type)).click();
        driverWait.until(elementFinishedResizing(liAccountAdd));
        liAccountAdd.findElement(By.id("newName")).sendKeys(ACCOUNT1);
        new Select(liAccountAdd.findElement(By.id("currency"))).selectByVisibleText(CURRENCY1);
        liAccountAdd.findElement(By.className("glyphicon-remove")).click();
        driverWait.until(elementFinishedResizing(liAccountAdd));
        Assert.assertEquals(section.findElements(By.tagName("li")).size(), beforeLi, "List shouldn't change.");
    }

    @Test(dataProvider = "typeProvider")
    public void testAddAccount(AccountVOType type) {
        LOGGER.debug("Test 'Add {} account' started.", type);
        for (String currency : currencyHelper.getCurrencies().keySet()) {
            addAccount(type, currency);
        }
        Assert.assertSame(getDefaultTotal(), BigDecimal.ZERO);
    }

    private void addAccount(AccountVOType type, String currency) {
        WebElement section = driver.findElement(sectionByAccountType(type));
        String accountName = type + "-" + currency;
        int before = section.findElements(By.tagName("li")).size();
        LOGGER.debug("Adding {} account '{}'.", type, accountName);
        WebElement liAccountAdd = driver.findElement(getAddLiName(type));
        liAccountAdd.findElement(getAddButtonName(type)).click();
        driverWait.until(elementFinishedResizing(liAccountAdd));

        liAccountAdd.findElement(By.id("newName")).sendKeys(accountName);
        new Select(liAccountAdd.findElement(By.id("currency"))).selectByVisibleText(currency);
        liAccountAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        LOGGER.debug("Finished adding {} account '{}'.", type, accountName);
        Assert.assertSame(section.findElements(By.tagName("li")).size(), before + 1, "Only one more " + type + " account.");
    }

    @Test(dataProvider = "typeProvider")
    public void testAddExistingAccount(AccountVOType type) {
        String accountName = type + ACCOUNT2;

        WebElement section = driver.findElement(sectionByAccountType(type));
        int beforeLi = section.findElements(By.tagName("li")).size();
        WebElement liAccountAdd = driver.findElement(getAddLiName(type));
        liAccountAdd.findElement(getAddButtonName(type)).click();
        driverWait.until(elementFinishedResizing(liAccountAdd));

        liAccountAdd.findElement(By.id("newName")).sendKeys(accountName);
        new Select(liAccountAdd.findElement(By.id("currency"))).selectByVisibleText(CURRENCY1);
        liAccountAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        Assert.assertEquals(section.findElements(By.tagName("li")).size(), beforeLi + 1, "One more account in the list.");

        liAccountAdd = driver.findElement(getAddLiName(type));
        liAccountAdd.findElement(getAddButtonName(type)).click();
        driverWait.until(elementFinishedResizing(liAccountAdd));

        liAccountAdd.findElement(By.id("newName")).sendKeys(accountName);
        new Select(liAccountAdd.findElement(By.id("currency"))).selectByVisibleText(CURRENCY2);
        liAccountAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        Assert.assertTrue(liAccountAdd.findElement(By.id("newName.errors")).isDisplayed());

        liAccountAdd.findElement(By.id("newName")).sendKeys(TOOLONG);
        liAccountAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        Assert.assertTrue(liAccountAdd.findElement(By.id("newName.errors")).isDisplayed());

        liAccountAdd.findElement(By.className("glyphicon-remove")).click();
        driverWait.until(elementFinishedResizing(liAccountAdd));

        Assert.assertEquals(section.findElements(By.tagName("li")).size(), beforeLi + 1, "Only one more account in the list.");
    }

    @AfterClass
    public void tearDownClass() {
        accountHelper.deleteAllAccounts();
        currencyHelper.deleteAllCurrencies();
    }
}
