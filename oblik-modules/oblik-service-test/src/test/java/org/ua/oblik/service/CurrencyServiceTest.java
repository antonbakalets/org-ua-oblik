package org.ua.oblik.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 * http://www.planetgeek.ch/wp-content/uploads/2013/06/Clean-Code-V2.1.pdf
 *
 * @author Anton Bakalets
 */
public class CurrencyServiceTest extends BaseServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceTest.class);

    private static ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    private CurrencyService currencyService;

    @Test
    public void listCurrencies() throws IOException {
        LOGGER.debug("Listing all currencies:");
        final List<CurrencyVO> currencies = currencyService.getCurrencies();
        for (CurrencyVO currency : currencies) {
            LOGGER.debug(mapper.writeValueAsString(currency));
        }
    }

    @Test
    public void getCurrency() {
        LOGGER.debug("Get currency:");
        final CurrencyVO euro = currencyService.createCurrency();
        final String UGH_SYMBOL = "UGH";
        euro.setSymbol(UGH_SYMBOL);
        euro.setRate(BigDecimal.valueOf(2.3));
        currencyService.save(euro);
        CurrencyVO newEuro = currencyService.getCurrency(euro.getCurrencyId());
        Assert.assertEquals("", euro.getCurrencyId(), newEuro.getCurrencyId());
        Assert.assertEquals("", euro.getSymbol(), newEuro.getSymbol());
        Assert.assertEquals("", euro.getRate().compareTo(newEuro.getRate()), 0);
        
        Assert.assertTrue("", currencyService.isDefaultExists());
        Assert.assertTrue("", currencyService.isSymbolExists(UGH_SYMBOL));
    }
}
