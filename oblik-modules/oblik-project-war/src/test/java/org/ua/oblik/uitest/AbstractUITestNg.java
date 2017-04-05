package org.ua.oblik.uitest;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.ua.oblik.embedded.EmbeddedServer;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.TransactionType;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 *
 */
public class AbstractUITestNg {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUITestNg.class);

    protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    public static final String CURRENCY1 = "ugh";
    public static final String CURRENCY2 = "usd";
    public static final String CURRENCY3 = "euro";

    public static final String ACCOUNT = "Account";

    public static final String ASSETS1 = "Assets-1";
    public static final String ASSETS2 = "Assets-2";
    public static final String EXPENSE1 = "Expense-1";
    public static final String EXPENSE2 = "Expense-2";
    public static final String INCOME1 = "Income-1";
    public static final String INCOME2 = "Income-2";

    protected WebDriver driver;
    protected String baseUrl;
    protected WebDriverWait driverWait;
    private Tomcat tomcat;

    @DataProvider(name = "accountTypeProvider")
    public static Iterator<Object[]> accountTypeProvider() {
        Set<Object[]> result = new HashSet<>();
        for (AccountVOType type : AccountVOType.values()) {
            result.add(new Object[]{type});
        }
        return result.iterator();
    }

    @DataProvider(name = "transactionTypeProvider")
    public static Iterator<Object []> transactionTypeProvider() {
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
        Set<String> selectOptions = optionsText(select);
        assertFalse(selectOptions.isEmpty(), "Nothing to compare.");
        assertEquals(expected, selectOptions, "Elements are not the same.");
    }

    private static Set<String> optionsText(Select currencySelect) {
        Set<String> result = new HashSet<>();
        for(WebElement element : currencySelect.getOptions()) {
            result.add(element.getText());
        }
        return result;
    }

    public static void assertDecimalEquals(BigDecimal value1, BigDecimal value2, String message) {
        assertNotNull(value1, "Can't compare null value.");
        assertNotNull(value2, "Can't compare null value.");
        Assert.assertEquals(value1.compareTo(value2), 0, message);
    }

    @BeforeSuite
    @Parameters({"port"})
    public void startEmbeddedServer(String port) throws ServletException, LifecycleException {
        tomcat = EmbeddedServer.getConfiguredTomcat(port);
        tomcat.start();
    }

    @BeforeTest
    @Parameters({"driverClassName", "host", "port"})
    @SuppressWarnings("unchecked")
    public void setUp(String driverClassName, String host, String port) throws Exception {
        Class<?> clazz = Class.forName(driverClassName);
        Constructor<WebDriver> constructor = (Constructor<WebDriver>) clazz.getConstructor();
        driver = constructor.newInstance();
        this.baseUrl = "http://" + host + ":" + port;
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driverWait = new WebDriverWait(driver, 10);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @AfterSuite
    public void stopEmbeddedServer() throws LifecycleException {
        tomcat.stop();
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

    protected void login(String username, String password) {
        driver.get(baseUrl);
        assertTrue(driver.getCurrentUrl().contains("login.html"), "Is redirected to login page.");
        fillLoginPage(username, password, false);
        driver.get(baseUrl + "/main.html");
        Assert.assertEquals(baseUrl + "/main.html", driver.getCurrentUrl(), "Page url.");
        driverWait.until(progressFinished());
    }

    protected void clickTransactionType(TransactionType type) {
        By byid = By.id("action-type-" + type.name().toLowerCase());
        driver.findElement(byid).click();
        driverWait.until(progressFinished());
    }
}
