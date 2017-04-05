package org.ua.oblik.uitest;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import org.junit.Assert;

/**
 *
 */
public class LoginUITestNg extends AbstractUITestNg {

    private static final Logger LOG = LoggerFactory.getLogger(LoginUITestNg.class);

    @Test
    public void testForgot() throws Exception {
        driver.get(baseUrl + "/login.html");
        LOG.info("Page title is {}.", driver.getTitle());

        driver.findElement(By.id("forgot-link")).click();

        Assert.assertEquals("Page url.", baseUrl + "/register/forgot.html", driver.getCurrentUrl());
    }

    @Test
    public void testRegister() throws Exception {
        driver.get(baseUrl + "/login.html");
        LOG.info("Page title is {}.", driver.getTitle());

        driver.findElement(By.id("register-link")).click();

        Assert.assertEquals("Page url.", baseUrl + "/register/register.html", driver.getCurrentUrl());
    }

    @Test
    @Parameters({ "username", "password" })
    public void testLogin(String username, String password) throws Exception {
        driver.get(baseUrl);
        LOG.info("Page title is {}.", driver.getTitle());

        fillLoginPage(username, password, true);

        LOG.info("Page url is {}.", driver.getCurrentUrl());
        Assert.assertEquals("Page url.", baseUrl + "/main.html", driver.getCurrentUrl());
    }

    @Test(dependsOnMethods = "testLogin")
    public void testLogout() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.id("logout-link")).click();
        Assert.assertEquals("Page url.", baseUrl + "/login.html?action=logout", driver.getCurrentUrl());
    }
}
