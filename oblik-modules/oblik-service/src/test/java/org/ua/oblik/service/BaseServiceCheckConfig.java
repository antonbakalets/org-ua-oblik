package org.ua.oblik.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.ua.oblik.service.test.TestHelperConfig;

/**
 * @author Anton Bakalets
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext // TODO @Rollback
//@Import(TestHelperConfig.class)
class BaseServiceCheckConfig {

}
