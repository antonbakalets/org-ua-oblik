package org.ua.oblik.uitest;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.ua.oblik.service.beans.AccountVOType;

/**
 *
 */
public class AssetsUITestNg extends AbstractUITestNg {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsUITestNg.class);

    private Map<AccountVOType, WebElement> accountSections;

    private Map<AccountVOType, WebElement> initAccountSections() {
        EnumMap<AccountVOType, WebElement> webElementMap = new EnumMap<>(AccountVOType.class);
        webElementMap.put(AccountVOType.ASSETS, driver.findElement(By.id("total-by-account")));
        webElementMap.put(AccountVOType.EXPENSE, driver.findElement(By.id("section-expenses")));
        webElementMap.put(AccountVOType.INCOME, driver.findElement(By.id("section-incomes")));
        return webElementMap;
    }

    @BeforeClass
    @Parameters({ "username", "password" })
    public void setUpClass(String username, String password) {
        login(username, password);
        accountSections = initAccountSections();
        deleteAllAccounts();
    }

    @AfterClass
    public void tearDownClass() {
        deleteAllAccounts();
    }

    @Test
    public void empty() {
        LOGGER.debug("stub");
    }

    private void deleteAllAccounts() {
        for (AccountVOType type : AccountVOType.values()) {
            deleteAllAccounts(type);
        }
    }

    private void deleteAllAccounts(AccountVOType type) {
        WebElement accountSection = sectionByAccountType(type);
        List<WebElement> currencyList = accountSection.findElements(By.tagName("li"));
        LOGGER.debug("Deleting {} currencies.", currencyList.size()-2);
        for (int i = currencyList.size() - 2; i > 0; i--) {
            WebElement li = currencyList.get(i);
            LOGGER.debug("Deleting currency number {}: {}", i, li.getText());
            li.findElement(By.tagName("a")).click();
            driverWait.until(elementFinishedResizing(li));
            WebElement trashBtn = li.findElement(By.className("glyphicon-trash"));
            Assert.assertTrue(trashBtn.isDisplayed());
            trashBtn.click();
            driverWait.until(elementFinishedResizing(li));
        }
    }

    private WebElement sectionByAccountType(AccountVOType type) {
        return accountSections.get(type);
    }
}
