package org.ua.oblik.uitest;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import junit.framework.Assert;

/**
 *
 */
public class CurrencyUITestNg extends AbstractUITestNg {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyUITestNg.class);
    private WebElement section;

    @BeforeClass
    @Parameters({ "username", "password" })
    public void login(String username, String password) {
        driver.get(baseUrl);
        Assert.assertTrue("Is redirected to login page.", driver.getCurrentUrl().contains("login.html"));
        fillLoginPage(username, password, false);
        driver.get(baseUrl + "/main.html");
        Assert.assertEquals("Page url.", baseUrl + "/main.html", driver.getCurrentUrl());
        section = driver.findElement(By.id("total-by-currency"));
    }

    @Test
    public void testAddCurrency() {
        int beforeLi = section.findElements(By.tagName("li")).size();
        addCurrency(getSymbol(), "02");
        Assert.assertEquals("There should be one more currency in the list.", beforeLi + 1, section.findElements(By.tagName("li")).size());
        addCurrency(getSymbol(), "03");
        Assert.assertEquals("There should be one more currency in the list.", beforeLi + 2, section.findElements(By.tagName("li")).size());
    }

    @Test
    public void testTryToAddCurrency() {
        WebElement liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        int beforeLi = section.findElements(By.tagName("li")).size();
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys("долар США");
        liCurrencyAdd.findElement(By.id("rate")).sendKeys("34");
        liCurrencyAdd.findElement(By.className("glyphicon-remove")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        Assert.assertEquals("List shouldn't change.", beforeLi, section.findElements(By.tagName("li")).size());
    }

    @Test
    public void testTryToAddExistingCurrency() {
        WebElement liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        int beforeLi = section.findElements(By.tagName("li")).size();
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys("долар США");
        liCurrencyAdd.findElement(By.id("rate")).sendKeys("34");
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(waitMillis(500));

        liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys("долар США");
        liCurrencyAdd.findElement(By.id("rate")).sendKeys("34");
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        Assert.assertTrue(driver.findElement(By.id("symbol.errors")).isDisplayed());
        liCurrencyAdd.findElement(By.className("glyphicon-remove")).click();

        Assert.assertEquals("There should be only one more currency in the list.", beforeLi + 1, section.findElements(By.tagName("li")).size());
        // TODO delete
    }

    @Test
    public void testAddWrongCurrency() {
        WebElement liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys("morethan10s");
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        Assert.assertTrue(driver.findElement(By.id("symbol.errors")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("rate.errors")).isDisplayed());

        liCurrencyAdd.findElement(By.id("rate")).sendKeys("rate");
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        Assert.assertTrue(driver.findElement(By.id("rate.errors")).isDisplayed());

        liCurrencyAdd.findElement(By.className("glyphicon-remove")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
    }

    @Test
    public void testDeleteAllCurrencies() {
        List<WebElement> currencyLis = section.findElements(By.tagName("li"));
        for (int i = 1; i < currencyLis.size() - 1; i++) {
            WebElement li = currencyLis.get(i);
            li.findElement(By.tagName("a")).click();
            driverWait.until(elementFinishedResizing(li));
            WebElement trashBtn = li.findElement(By.className("glyphicon-trash"));
            Assert.assertTrue(trashBtn.isDisplayed());
        }
    }

    @Test(dependsOnMethods = "testDeleteAllCurrencies")
    public void addDefaultCurrency() {

    }

    private String getSymbol() {
        String s = "a" + new Random().nextInt();
        return s.substring(0, 9);
    }

    private void addCurrency(CharSequence symbol, CharSequence rate) {
        LOGGER.debug("Adding new currency {} with rate {}.", symbol, rate);
        WebElement liCurrencyAdd = driver.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        driverWait.until(elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys(symbol);
        liCurrencyAdd.findElement(By.id("rate")).sendKeys(rate);
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        driverWait.until(waitMillis(500));
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

    @Deprecated
    /**
     * @deprecated it's a bad idea
     */
    public static ExpectedCondition<Boolean> waitMillis(final Integer millis) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    LOGGER.warn(e.getMessage());
                }
                return Boolean.TRUE;
            }
        };
    }
}
