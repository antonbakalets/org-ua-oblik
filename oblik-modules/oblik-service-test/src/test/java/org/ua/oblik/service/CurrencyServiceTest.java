package org.ua.oblik.service;

import org.ua.oblik.service.test.CurrencyServiceTestHelper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.CurrencyVO;
import org.ua.oblik.service.test.DefinedCurrency;

/**
 * http://www.planetgeek.ch/wp-content/uploads/2013/06/Clean-Code-V2.1.pdf
 *
 * @author Anton Bakalets
 */
public class CurrencyServiceTest extends BaseServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceTest.class);

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyServiceTestHelper currencyServiceTestHelper;

    @Test
    public void createCurrencies() {
        LOGGER.debug("Check that database is empty.");
        final List<CurrencyVO> empty = currencyService.getCurrencies();
        Assert.assertTrue("Test suppose to start from empty database", empty.isEmpty());

        try {
            currencyService.getDefaultCurrency();
            Assert.fail("No default currency in empty.");
        } catch (EntityNotFoundException enfe) {
            LOGGER.debug(enfe.getMessage()); // OK
        }

        LOGGER.debug("Saving default currency.");
        currencyServiceTestHelper.createAndSaveAsDefault(DefinedCurrency.UGH);

        try {
            LOGGER.debug("Saving default currency once again.");
            currencyServiceTestHelper.createAndSaveAsDefault(DefinedCurrency.UGH);
            Assert.fail("Default currency can be saved only once.");
        } catch (RuntimeException re) {
            LOGGER.debug(re.getMessage());
        }

        LOGGER.debug("Retrieving default currency:");
        final CurrencyVO defaultCurrency = currencyService.getDefaultCurrency();
        Assert.assertNotNull(defaultCurrency);
        LOGGER.debug(defaultCurrency.toString());
        Assert.assertEquals("Rate of default currency is always equals 1.", defaultCurrency.getRate(), BigDecimal.ONE);
        Assert.assertEquals("Default currency symbol is грн.", defaultCurrency.getSymbol(), DefinedCurrency.UGH.getSymbol());

        LOGGER.debug("Saving dollar.");
        final CurrencyVO dollar = currencyServiceTestHelper.createAndSaveCurrency(DefinedCurrency.USD);
        Assert.assertEquals("", dollar.getSymbol(), DefinedCurrency.USD.getSymbol());
        Assert.assertEquals("", dollar.getRate(), DefinedCurrency.USD.getRate());

        LOGGER.debug("Saving euro.");
        final CurrencyVO euro = currencyServiceTestHelper.createAndSaveCurrency(DefinedCurrency.EUR);
        Assert.assertEquals("", euro.getSymbol(), euro.getSymbol());
        Assert.assertEquals("", euro.getRate(), euro.getRate());
    }

    @Test
    public void listCurrencies() {
        LOGGER.debug("Listing all currencies:");
        final List<CurrencyVO> currencies = currencyService.getCurrencies();
        Assert.assertEquals("Three currencies are present: gryvnia, dollar and euro", currencies.size(), 3);
        for (CurrencyVO currency : currencies) {
            LOGGER.debug(currency.toString());
        }
    }

    @Test
    public void getCurrency() {
        LOGGER.debug("Get currency:");
        final CurrencyVO euro = currencyServiceTestHelper.createAndSaveCurrency(DefinedCurrency.EUR);
        CurrencyVO newEuro = currencyService.getCurrency(euro.getCurrencyId());
        Assert.assertEquals("", euro.getCurrencyId(), newEuro.getCurrencyId());
        Assert.assertEquals("", euro.getSymbol(), newEuro.getSymbol());
        Assert.assertEquals("", euro.getRate(), newEuro.getRate());
    }
}
