package org.ua.oblik.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Anton Bakalets
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CurrencyServiceTest.class,
        AccountServiceTest.class,
        TransactionServiceTest.class,
        MultiThreadTransactionTest.class
})
public class ServiceSuiteTest {

}
