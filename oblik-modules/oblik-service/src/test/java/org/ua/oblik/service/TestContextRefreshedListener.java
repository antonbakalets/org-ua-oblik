package org.ua.oblik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;

/**
 *
 * @author Anton Bakalets
 */
@Component
public class TestContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestContextRefreshedListener.class);
    
    @Autowired
    private CurrencyServiceTestHelper currencyHelper;
    
    @Autowired
    private AccountServiceTestHelper accountHelper;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        LOGGER.debug("[TEST] Test context started.");
        LOGGER.debug("[TEST] Saving predefined currencies.");
        currencyHelper.saveDefinedCurrencies();
        LOGGER.debug("[TEST] Saving predefined accounts.");
        accountHelper.saveDefinedAccounts();
        LOGGER.debug("[TEST] Database ready.");
    }
    
}
