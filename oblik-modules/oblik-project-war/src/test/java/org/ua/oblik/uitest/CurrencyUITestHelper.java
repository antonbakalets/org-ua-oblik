package org.ua.oblik.uitest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 *
 */
class CurrencyUITestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyUITestHelper.class);
    private final AbstractUITestNg uiTestNg;

    private Map<String, BigDecimal> available;
    private String availableDefault;

    public CurrencyUITestHelper(AbstractUITestNg uiTestNg) {
        this.uiTestNg = uiTestNg;
        available = new HashMap<>();
    }

    void addDefaultCurrency(CharSequence symbol) {
        Assert.assertTrue(available.isEmpty(), "No currencies before adding default.");
        LOGGER.debug("Adding default currency {}.", symbol);
        WebElement section = uiTestNg.driver.findElement(By.id("total-by-currency"));
        WebElement liCurrencyAdd = section.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        uiTestNg.driverWait.until(AbstractUITestNg.elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys(symbol);
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        uiTestNg.driverWait.until(AbstractUITestNg.progressFinished());

        availableDefault = symbol.toString();
        available.put(availableDefault, BigDecimal.ONE);
        LOGGER.debug("Finished adding default currency {}.", symbol);
    }

    void addCurrency(CharSequence symbol, CharSequence rate) {
        LOGGER.debug("Adding new currency {} with rate {}.", symbol, rate);
        WebElement section = uiTestNg.driver.findElement(By.id("total-by-currency"));
        WebElement liCurrencyAdd = section.findElement(By.id("li-currency-add"));
        liCurrencyAdd.findElement(By.id("add-currency-btn")).click();
        uiTestNg.driverWait.until(AbstractUITestNg.elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("symbol")).sendKeys(symbol);
        liCurrencyAdd.findElement(By.id("rate")).sendKeys(rate);
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        uiTestNg.driverWait.until(AbstractUITestNg.progressFinished());

        available.put(symbol.toString(), new BigDecimal(rate.toString()));
        LOGGER.debug("Finished adding currency {}.", symbol);
    }

    void deleteAllCurrencies() {
        WebElement section = uiTestNg.driver.findElement(By.id("total-by-currency"));
        List<WebElement> currencyList = section.findElements(By.tagName("li"));
        LOGGER.debug("Deleting {} currencies.", currencyList.size()-2);
        for (int i = currencyList.size() - 2; i > 0; i--) {
            WebElement li = currencyList.get(i);
            LOGGER.debug("Deleting currency number {}: {}", i, li.getText());
            li.findElement(By.tagName("a")).click();
            uiTestNg.driverWait.until(AbstractUITestNg.elementFinishedResizing(li));
            String symbol = li.findElement(By.id("symbol")).getText();
            WebElement trashBtn = li.findElement(By.className("glyphicon-trash"));
            Assert.assertTrue(trashBtn.isDisplayed(), "Account should be removable.");
            trashBtn.click();
            uiTestNg.driverWait.until(AbstractUITestNg.elementFinishedResizing(li));
            available.remove(symbol);
        }
        availableDefault = null;
    }

    public String getDefaultCurrency() {
        if (availableDefault == null) {
            throw new RuntimeException("Default currency not set.");
        } else {
            return availableDefault;
        }
    }

    public Map<String, BigDecimal> getCurrencies() {
        return available;
    }
}
