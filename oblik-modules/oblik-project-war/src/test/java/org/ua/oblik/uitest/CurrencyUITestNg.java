package org.ua.oblik.uitest;

import java.math.BigDecimal;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 */
public class CurrencyUITestNg extends AbstractUITestNg {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyUITestNg.class);
    private static final String CURRENCY_DEFAULT = "грн.";

    private WebElement section;

    @BeforeClass
    @Parameters({ "username", "password" })
    public void login(String username, String password) {
        driver.get(baseUrl);
        Assert.assertTrue(driver.getCurrentUrl().contains("login.html"), "Is redirected to login page.");
        fillLoginPage(username, password, false);
        driver.get(baseUrl + "/main.html");
        Assert.assertEquals(baseUrl + "/main.html", driver.getCurrentUrl(), "Page url.");
        section = driver.findElement(By.id("total-by-currency"));
    }

    @Test
    public void testAddDefaultCurrency() {
        Assert.assertSame(section.findElements(By.tagName("li")).size(), 2, "There are no currencies at first test.");
        LOGGER.debug("Adding default currency '{}'.", CURRENCY_DEFAULT);
        WebElement liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));

        Assert.assertFalse(liCurrencyAdd.findElement(By.id("rate")).isDisplayed());
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys(CURRENCY_DEFAULT);
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        LOGGER.debug("Finished adding default currency '{}'.", CURRENCY_DEFAULT);
        Assert.assertSame(section.findElements(By.tagName("li")).size(), 3, "Default currency only.");

        Assert.assertSame(getDefaultTotal(), BigDecimal.ZERO);
    }

    @Test(dependsOnMethods = "testAddDefaultCurrency")
    public void testAddCurrency() {
        int beforeLi = section.findElements(By.tagName("li")).size();
        addCurrency("USD", "02");
        Assert.assertEquals(section.findElements(By.tagName("li")).size(), beforeLi + 1, "There should be one more currency in the list.");
        addCurrency("euro", "3,4");
        Assert.assertEquals(section.findElements(By.tagName("li")).size(), beforeLi + 2, "There should be one more currency in the list.");
    }

    @Test(dependsOnMethods = "testAddCurrency")
    public void testTryToAddCurrency() {
        WebElement liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        int beforeLi = section.findElements(By.tagName("li")).size();
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys("долар США");
        liCurrencyAdd.findElement(By.id("rate")).sendKeys("341");
        liCurrencyAdd.findElement(By.className("glyphicon-remove")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        Assert.assertEquals(section.findElements(By.tagName("li")).size(), beforeLi, "List shouldn't change.");
    }

    @Test(dependsOnMethods = "testTryToAddCurrency")
    public void testTryToAddExistingCurrency() {
        WebElement liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        int beforeLi = section.findElements(By.tagName("li")).size();
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));

        liCurrencyAdd.findElement(By.id("symbol")).sendKeys("долар США");
        liCurrencyAdd.findElement(By.id("rate")).sendKeys("34");
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        Assert.assertEquals(section.findElements(By.tagName("li")).size(), beforeLi + 1, "One more currency in the list.");

        liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));

        liCurrencyAdd.findElement(By.id("symbol")).sendKeys("долар США");
        liCurrencyAdd.findElement(By.id("rate")).sendKeys("34");
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        Assert.assertTrue(liCurrencyAdd.findElement(By.id("symbol.errors")).isDisplayed());
        liCurrencyAdd.findElement(By.className("glyphicon-remove")).click();

        Assert.assertEquals(section.findElements(By.tagName("li")).size(), beforeLi + 1, "Only one more currency in the list.");
        // TODO delete
    }

    @Test(dependsOnMethods = "testTryToAddExistingCurrency")
    public void testAddWrongCurrency() {
        WebElement liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys("morethan10s");
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        Assert.assertTrue(driver.findElement(By.id("symbol.errors")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("rate.errors")).isDisplayed());

        liCurrencyAdd.findElement(By.id("rate")).sendKeys("rate");
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        Assert.assertTrue(driver.findElement(By.id("rate.errors")).isDisplayed());

        liCurrencyAdd.findElement(By.className("glyphicon-remove")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
    }

    /*@Test(dependsOnMethods = "testAddWrongCurrency")
    public void testDeleteAllCurrencies() {
        List<WebElement> currencyLis = section.findElements(By.tagName("li"));
        for (int i = 1; i < currencyLis.size() - 1; i++) {
            WebElement li = currencyLis.get(i);
            li.findElement(By.tagName("a")).click();
            driverWait.until(elementFinishedResizing(li));
            WebElement trashBtn = li.findElement(By.className("glyphicon-trash"));
            Assert.assertTrue(trashBtn.isDisplayed());
        }
    }*/

    private String getSymbol() {
        String s = "a" + new Random().nextInt();
        return s.substring(0, 9);
    }

    private void addCurrency(CharSequence symbol, CharSequence rate) {
        LOGGER.debug("Adding new currency {} with rate {}.", symbol, rate);
        WebElement liCurrencyAdd = section.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys(symbol);
        liCurrencyAdd.findElement(By.id("rate")).sendKeys(rate);
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(progressFinished());
        LOGGER.debug("Finished adding currency {}.", symbol);
    }

    public static ExpectedCondition<Boolean> elementFinishedResizing(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Dimension initialSize = element.getSize();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    LOGGER.warn(e.getMessage());
                }
                Dimension finalSize = element.getSize();
                return initialSize.equals(finalSize);
            }
        };
    }

    public static ExpectedCondition<Boolean> progressFinished() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                WebElement progress = driver.findElement(By.id("main-progress"));
                return !progress.isDisplayed();
            }
        };
    }
}
