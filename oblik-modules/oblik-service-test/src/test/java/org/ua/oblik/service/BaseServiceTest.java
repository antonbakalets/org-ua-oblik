package org.ua.oblik.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.ua.oblik.context.ServiceTestConfig;

/**
 *
 * @author Anton Bakalets
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    ServiceTestConfig.SERVICE_TEST_CONTEXT,
    ServiceTestConfig.JPA_TEST_CONTEXT,
    ServiceTestConfig.DAO_CONTEXT,
    ServiceTestConfig.SERVICE_CONTEXT
})
@TransactionConfiguration(
        transactionManager = ServiceTestConfig.TRANSACTION_MANAGER,
        defaultRollback = ServiceTestConfig.DEFAULT_ROLLBACK)
public class BaseServiceTest {
    
}
