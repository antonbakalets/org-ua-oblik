package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.Date;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;
import org.ua.oblik.service.test.DefinedAccount;
import org.ua.oblik.service.test.DefinedCurrency;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionServiceListTest extends BaseServiceTest {

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
