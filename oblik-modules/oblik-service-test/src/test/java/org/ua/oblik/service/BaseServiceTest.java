package org.ua.oblik.service;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(transactionManager = ServiceTestConfig.TRANSACTION_MANAGER)
@Rollback
abstract class BaseServiceTest {
    
}
