package org.ua.oblik.uitest;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    @BeforeClass
    @Parameters({ "username", "password" })
    public void login(String username, String password) {
        driver.get(baseUrl);
        Assert.assertTrue("Is redirected to login page.", driver.getCurrentUrl().contains("login.html"));
        fillLoginPage(username, password, false);
        driver.get(baseUrl + "/main.html");
        Assert.assertEquals("Page url.", baseUrl + "/main.html", driver.getCurrentUrl());
    }

    @Test
    public void addDefaultCurrency() {

    }

    @Test
    public void testCurrency() throws InterruptedException {
        WebElement section = driver.findElement(By.id("total-by-currency"));
        int beforeLi = section.findElements(By.tagName("li")).size();
        addCurrency(getSymbol(), "02");
        Assert.assertEquals("There should be one more currency in the list.", beforeLi + 1, section.findElements(By.tagName("li")).size());
        addCurrency(getSymbol(), "03");
        Assert.assertEquals("There should be one more currency in the list.", beforeLi + 2, section.findElements(By.tagName("li")).size());
    }

    private String getSymbol() {
        String s = "a" + (new Random().nextInt() - 1000000);
        return s.substring(0, 9);
    }

    private void addCurrency(CharSequence symbol, CharSequence rate) {
        LOGGER.debug("Adding new currency {} with rate {}.", symbol, rate);
        WebElement liCurrecyAdd = driver.findElement(By.id("li-currency-add"));
        liCurrecyAdd.findElement(By.id("add-currency-btn")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(elementFinishedResizing(liCurrecyAdd));
        liCurrecyAdd.findElement(By.id("symbol")).sendKeys(symbol);
        liCurrecyAdd.findElement(By.id("rate")).sendKeys(rate);
        liCurrecyAdd.findElement(By.className("glyphicon-ok")).click();
        wait.until(waitMillis(500));
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
