package org.ua.oblik.uitest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import junit.framework.Assert;

/**
 *
 */
public class LoginUITestNg extends AbstractUITestNg {

    private static final Logger LOG = LoggerFactory.getLogger(LoginUITestNg.class);

    @Test
    @Parameters({ "username", "password" })
    public void testLogin(String username, String password) throws Exception {
        driver.get(baseUrl + "/login.html");
        LOG.info("Page title is {}.", driver.getTitle());

        fillLoginPage(username, password, true);

        LOG.info("Page url is {}.", driver.getCurrentUrl());
        Assert.assertEquals("Page url.", baseUrl + "/main.html", driver.getCurrentUrl());
    }

}
