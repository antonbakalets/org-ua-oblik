package org.ua.oblik.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author abakalets
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    CurrencyServiceTest.class,
    AccountServiceTest.class,
    TransactionServiceTest.class
})
public class ServiceSuiteTest {
    
}
