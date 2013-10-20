package org.ua.oblik.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.CurrencyVO;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;
import org.ua.oblik.service.test.DefinedCurrency;

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

    @Autowired
    private CurrencyServiceTestHelper currencyServiceTestHelper;

    @Test
    public void getDefaultCurrency() {
        LOGGER.debug("Get default currency.");
        final CurrencyVO expected = currencyService.getDefaultCurrency();
        final CurrencyVO actual = currencyServiceTestHelper.getDefinedCurrency(DefinedCurrency.values()[0]);
        Assert.assertEquals("", expected.getCurrencyId(), actual.getCurrencyId());
        Assert.assertEquals("", expected.getSymbol(), actual.getSymbol());
        Assert.assertEquals("", expected.getRate().compareTo(BigDecimal.ONE), 0);
        Assert.assertTrue("", expected.getDefaultRate());
    }
    
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
        LOGGER.debug("Getting currency.");
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
    
    @Test
    public void isSymbolExists() {
        LOGGER.debug("checking is currency symbol exists.");
        final CurrencyVO currency = currencyService.getDefaultCurrency();
        Assert.assertTrue(currencyService.isSymbolExists(currency.getSymbol()));
        Assert.assertFalse(currencyService.isSymbolExists(UUID.randomUUID().toString()));
    }

    @Test
    public void updateCurrency() {
        LOGGER.debug("Updating currency");
        final CurrencyVO currency = currencyServiceTestHelper.getDefinedCurrency(DefinedCurrency.EUR);
        final String newSymbol = currency.getSymbol() + " (new)";
        currency.setSymbol(newSymbol);
        currencyService.save(currency);
        Assert.assertEquals(newSymbol, currency.getSymbol());
    }
}
