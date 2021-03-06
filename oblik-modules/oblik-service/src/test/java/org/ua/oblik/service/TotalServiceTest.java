package org.ua.oblik.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.CurrencyVO;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;

/**
 * http://www.planetgeek.ch/wp-content/uploads/2013/06/Clean-Code-V2.1.pdf
 *
 * @author Anton Bakalets
 */
public class TotalServiceTest extends BaseServiceCheckConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TotalServiceTest.class);

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private TotalService totalService;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private CurrencyServiceTestHelper currencyServiceTestHelper;
    
    @Test
    public void listTotals() throws IOException {
        LOGGER.debug("Spending all currencies:");
        final List<CurrencyVO> currencies = currencyService.getCurrencies();
        for (CurrencyVO currency : currencies) {
            
            final BigDecimal total = totalService.getCurrencyTotal(currency.getCurrencyId());
            LOGGER.debug("Currency" + currency + " total: " + total);
            
            
            
        }
    }

    
}
