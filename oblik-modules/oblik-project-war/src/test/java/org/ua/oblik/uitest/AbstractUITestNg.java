package org.ua.oblik.uitest;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.TransactionType;

/**
 *
 */
public class AbstractUITestNg {

    public static final String ACCOUNT1 = "account1";
    public static final String ACCOUNT2 = "account2";
    public static final String CURRENCY1 = "грн";
    public static final String CURRENCY2 = "usd";
    public static final String CURRENCY3 = "euro";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUITestNg.class);

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    protected WebDriver driver;
    protected String baseUrl;
    protected WebDriverWait driverWait;

    @DataProvider(name = "accountTypeProvider")
    public static Iterator<Object[]> accountTypeProvider() {
        Set<Object[]> result = new HashSet<>();
        for (AccountVOType type : AccountVOType.values()) {
            result.add(new Object[]{type});
        }
        return result.iterator();
    }

    @DataProvider(name = "transactionTypeProvider")
    public static Iterator<Object[]> transactionTypeProvider() {
        Set<Object[]> result = new HashSet<>();
        for (TransactionType type : TransactionType.values()) {
            result.add(new Object[]{type});
        }
        return result.iterator();
    }

    @DataProvider(name = "transactionAndAccountTypeProvider")
    public static Iterator<Object[]> transactionAndAccountTypeProvider() {
        Set<Object[]> result = new HashSet<>();
        result.add(new Object[]{TransactionType.EXPENSE, AccountVOType.ASSETS, AccountVOType.EXPENSE});
        result.add(new Object[]{TransactionType.TRANSFER, AccountVOType.ASSETS, AccountVOType.ASSETS});
        result.add(new Object[]{TransactionType.INCOME, AccountVOType.INCOME, AccountVOType.ASSETS});
        return result.iterator();
    }

    protected static void assertSameOptions(Select select, Set<String> expected) {
        Set<String> intersection = new HashSet<>(expected);
        Assert.assertTrue(intersection.retainAll(optionsText(select)), "Nothing to compare.");
        Assert.assertTrue(intersection.isEmpty(), "Elements are not the same.");
    }

    private static Set<String> optionsText(Select currencySelect) {
        Set<String> result = new HashSet<>();
        for(WebElement element : currencySelect.getOptions()) {
            result.add(element.getText());
        }
        return result;
    }

    @BeforeTest
    @Parameters({"driverClassName", "base.url"})
    public void setUp(String driverClassName, String baseUrl) throws Exception {
        Class<?> clazz = Class.forName(driverClassName);
        Constructor<WebDriver> ctor = (Constructor<WebDriver>) clazz.getConstructor();
        driver = ctor.newInstance();
        this.baseUrl = baseUrl;
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driverWait = new WebDriverWait(driver, 10);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
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

    protected void fillLoginPage(String username, String password, boolean rememberMe) {
        WebElement usernameElem = driver.findElement(By.id("username"));
        usernameElem.sendKeys(username);

        WebElement passwordElem = driver.findElement(By.id("password"));
        passwordElem.sendKeys(password);

        if (rememberMe) {
            WebElement rememberMeElem = driver.findElement(By.id("login-rememberme"));
            rememberMeElem.click();
        }

        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.submit();
    }

    protected BigDecimal getDefaultTotal() {
        BigDecimal total = null;
        try {
            DECIMAL_FORMAT.setParseBigDecimal(true);
            String textTotal = driver.findElement(By.id("default-total-value")).getText();
            LOGGER.info("Total '{}'.", textTotal);
            total = (BigDecimal) DECIMAL_FORMAT.parse(textTotal);
        } catch (ParseException e) {
            Assert.fail("Could not parse default total.");
        }
        return total;
    }

    protected String getDefaultCurrency() {
        return "TODO";
    }

    protected void login(String username, String password) {
        driver.get(baseUrl);
        Assert.assertTrue(driver.getCurrentUrl().contains("login.html"), "Is redirected to login page.");
        fillLoginPage(username, password, false);
        driver.get(baseUrl + "/main.html");
        Assert.assertEquals(baseUrl + "/main.html", driver.getCurrentUrl(), "Page url.");
        driverWait.until(progressFinished());
    }
}
