package org.ua.oblik.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
public class CurrencyServiceTest extends BaseServiceCheckConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceTest.class);

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private TotalService totalService;
    
    @Autowired
    private CurrencyServiceTestHelper currencyServiceTestHelper;

    @Test
    public void getDefaultCurrency() {
        LOGGER.debug("[TEST] Get default currency.");
        final CurrencyVO expected = currencyService.getDefaultCurrency();
        final CurrencyVO actual = currencyServiceTestHelper.getDefinedCurrency(DefinedCurrency.values()[0]);
        Assert.assertEquals("", expected.getCurrencyId(), actual.getCurrencyId());
        Assert.assertEquals("", expected.getSymbol(), actual.getSymbol());
        Assert.assertEquals("", 0, expected.getRate().compareTo(BigDecimal.ONE));
        Assert.assertTrue("", expected.getDefaultRate());
        
        final BigDecimal total = totalService.getDefaultCurrencyTotal();
        LOGGER.debug("[TEST] Default currency total: " + total);
    }
    
    @Test
    public void listCurrencies() throws IOException {
        LOGGER.debug("[TEST] Listing all currencies:");
        final List<CurrencyVO> currencies = currencyService.getCurrencies();
        
        for (CurrencyVO currency : currencies) {
            LOGGER.debug("[TEST] Currency " + currency);
            final BigDecimal total = totalService.getCurrencyTotal(currency.getCurrencyId());
            LOGGER.debug("[TEST] Currency" + currency + " toatal: " + total);
        }
    }

    @Test
    public void getCurrency() {
        LOGGER.debug("[TEST] Getting currency.");
        final CurrencyVO euro = currencyService.createCurrency();
        final String UGH_SYMBOL = "UGH";
        euro.setSymbol(UGH_SYMBOL);
        euro.setRate(BigDecimal.valueOf(2.3));
        try {
            currencyService.save(euro);
        } catch (Exception e) {
            LOGGER.debug("Ignored.", e);
        }
        CurrencyVO newEuro = currencyService.getCurrency(euro.getCurrencyId());
        Assert.assertEquals("", euro.getCurrencyId(), newEuro.getCurrencyId());
        Assert.assertEquals("", euro.getSymbol(), newEuro.getSymbol());
        Assert.assertEquals("", 0, euro.getRate().compareTo(newEuro.getRate()));

        Assert.assertTrue("", currencyService.isDefaultExists());
        Assert.assertTrue("", currencyService.isSymbolExists(UGH_SYMBOL));
    }
    
    @Test
    public void isSymbolExists() {
        LOGGER.debug("[TEST] Checking is currency symbol exists.");
        final CurrencyVO currency = currencyService.getDefaultCurrency();
        Assert.assertTrue(currencyService.isSymbolExists(currency.getSymbol()));
        Assert.assertFalse(currencyService.isSymbolExists(UUID.randomUUID().toString()));
    }

    @Test
    public void updateCurrency() {
        LOGGER.debug("[TEST] Updating currency");
        final CurrencyVO currency = currencyServiceTestHelper.getDefinedCurrency(DefinedCurrency.EUR);
        final String newSymbol = currency.getSymbol() + " (new)";
        currency.setSymbol(newSymbol);
        try {
            currencyService.save(currency);
        } catch (Exception e) {
            LOGGER.debug("Ignored.", e);
        }
        Assert.assertEquals(newSymbol, currency.getSymbol());
    }

    @Test
    public void createCurrency() {
        LOGGER.debug("[TEST] Creating currency");
        CurrencyVO currency = currencyService.createCurrency();
        Assert.assertFalse(currency.getDefaultRate());
    }

    @Test(expected = NotFoundException.class)
    public void removeUnknownCurrency() throws NotFoundException, BusinessConstraintException {
        currencyService.remove(-15);
    }

    @Test(expected = BusinessConstraintException.class)
    public void removeUnremovableCurrency() throws NotFoundException, BusinessConstraintException {
        currencyService.remove(currencyServiceTestHelper.currencyId(DefinedCurrency.UGH));
    }

    @Test
    public void removeRemovableCurrency() throws NotFoundException, BusinessConstraintException {
        currencyService.remove(currencyServiceTestHelper.currencyId(DefinedCurrency.TBD));
    }
}
