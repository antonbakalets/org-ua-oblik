package org.ua.oblik.uitest;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

/**
 *
 */
public class AbstractUITestNg {

    protected WebDriver driver;
    protected String baseUrl;

    @BeforeTest
    @Parameters({ "driverClassName", "base.url" })
    public void setUp(String driverClassName, String baseUrl) throws Exception {
        Class<?> clazz = Class.forName(driverClassName);
        Constructor<WebDriver> ctor = (Constructor<WebDriver>)clazz.getConstructor();
        driver = ctor.newInstance();
        this.baseUrl = baseUrl;
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    protected void fillLoginPage(String username, String password, boolean rememberMe) {
        WebElement usernameElem = driver.findElement(By.id("username"));
        usernameElem.sendKeys(username);

        WebElement passwordElem = driver.findElement(By.id("password"));
        passwordElem.sendKeys(password);

        if (rememberMe) {
            WebElement rememberMeElem = driver.findElement(By.id("rememberme"));
            rememberMeElem.click();
        }

        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.submit();
    }
}
