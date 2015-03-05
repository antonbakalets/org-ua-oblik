package org.ua.oblik.uitest;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author Anton Bakalets
 */
public class LoginUITest {
        
    private WebDriver driver;
    
    @Before
    public void setUp()  {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }
    
    @Test
    public void loginFailTest() {
        driver.get("http://localhost:8080/login.html");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("paco");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("hola");

        password.submit();

        System.out.println("Page title is: " + driver.getTitle());

        driver.quit();
    }
}
