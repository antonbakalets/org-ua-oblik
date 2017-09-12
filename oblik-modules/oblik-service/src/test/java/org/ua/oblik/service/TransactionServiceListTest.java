package org.ua.oblik.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionServiceListTest extends BaseServiceCheckConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceListTest.class);

    @Autowired
    private CurrencyServiceTestHelper cH;

    @Autowired
    private AccountServiceTestHelper aH;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TotalService totalService;
    
    
    
    @Test
    public void insertIncome() {
        LOGGER.debug("[TEST] insertIncome");
        
    }
 
    
}
