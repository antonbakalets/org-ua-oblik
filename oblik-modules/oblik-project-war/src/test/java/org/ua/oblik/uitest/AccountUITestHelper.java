package org.ua.oblik.uitest;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.ua.oblik.service.beans.AccountVOType;

/**
 *
 */
class AccountUITestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountUITestHelper.class);

    private static final Map<AccountVOType, By> accountSections = initAccountSections();
    private static final Map<AccountVOType, By> accountAddButtons = initAccountButtons();

    private final AbstractUITestNg uiTestNg;
    //private final Map<String, String> available;

    public AccountUITestHelper(AbstractUITestNg uiTestNg) {
        this.uiTestNg = uiTestNg;
        //this.available = new HashMap<>();
    }

    private static Map<AccountVOType, By> initAccountSections() {
        EnumMap<AccountVOType, By> webElementMap = new EnumMap<>(AccountVOType.class);
        webElementMap.put(AccountVOType.ASSETS, By.id("total-by-account"));
        webElementMap.put(AccountVOType.EXPENSE, By.id("section-expenses"));
        webElementMap.put(AccountVOType.INCOME, By.id("section-incomes"));
        return webElementMap;
    }

    private static Map<AccountVOType, By> initAccountButtons() {
        EnumMap<AccountVOType, By> result = new EnumMap<>(AccountVOType.class);
        result.put(AccountVOType.ASSETS, By.id("add-assets"));
        result.put(AccountVOType.EXPENSE, By.id("add-expense"));
        result.put(AccountVOType.INCOME, By.id("add-income"));
        return result;
    }

    static By sectionByAccountType(AccountVOType type) {
        return accountSections.get(type);
    }

    static By getAddButtonName(AccountVOType type) {
        return accountAddButtons.get(type);
    }

    static By getAddLiName(AccountVOType type) {
        return By.id("li-" + type.name().toLowerCase() + "-add");
    }

    void addAccount(AccountVOType type, String newName, String currency) {
        LOGGER.debug("Adding new account {} with currency {}.", newName, currency);
        WebElement liCurrencyAdd = uiTestNg.driver.findElement(getAddLiName(type));
        liCurrencyAdd.findElement(getAddButtonName(type)).click();
        uiTestNg.driverWait.until(AbstractUITestNg.elementFinishedResizing(liCurrencyAdd));
        liCurrencyAdd.findElement(By.id("newName")).sendKeys(newName);
        new Select(liCurrencyAdd.findElement(By.id("currency"))).selectByVisibleText(currency);
        liCurrencyAdd.findElement(By.className("glyphicon-ok")).click();
        uiTestNg.driverWait.until(AbstractUITestNg.progressFinished());
        LOGGER.debug("Finished adding account {}.", newName);
    }

    void deleteAllAccounts() {
        for (AccountVOType type : AccountVOType.values()) {
            deleteAllAccounts(type);
        }
    }

    private void deleteAllAccounts(AccountVOType type) {
        WebElement accountSection = uiTestNg.driver.findElement(sectionByAccountType(type));
        List<WebElement> currencyList = accountSection.findElements(By.tagName("li"));
        LOGGER.debug("Deleting {} accounts.", currencyList.size()-2);
        for (int i = currencyList.size() - 2; i > 0; i--) {
            WebElement li = currencyList.get(i);
            LOGGER.debug("Deleting account number {}: {}", i, li.getText());
            li.findElement(By.tagName("a")).click();
            uiTestNg.driverWait.until(AbstractUITestNg.elementFinishedResizing(li));
            WebElement trashBtn = li.findElement(By.className("glyphicon-trash"));
            Assert.assertTrue(trashBtn.isDisplayed());
            trashBtn.click();
            uiTestNg.driverWait.until(AbstractUITestNg.elementFinishedResizing(li));
        }
    }
}
