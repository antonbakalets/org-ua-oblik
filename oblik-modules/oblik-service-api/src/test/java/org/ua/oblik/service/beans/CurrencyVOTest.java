package org.ua.oblik.service.beans;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CurrencyVOTest {

    private CurrencyVO currencyVO;

    @Before
    public void setUp() {
        currencyVO = new CurrencyVO();
    }

    @Test
    public void testCurrencyId() {
        currencyVO.setCurrencyId(15);
        assertEquals("Currency id.", Integer.valueOf(15), currencyVO.getCurrencyId());
    }

    @Test
    public void testRate() {
        currencyVO.setRate(BigDecimal.TEN);
        assertEquals("Currency rate.", BigDecimal.TEN, currencyVO.getRate());
    }

    @Test
    public void testSymbol() {
        currencyVO.setSymbol("S");
        assertEquals("Currency symbol.", "S", currencyVO.getSymbol());
    }

    @Test
    public void testDefaultRate() {
        currencyVO.setDefaultRate(true);
        assertEquals("Currency default.", true, currencyVO.getDefaultRate());
    }

    @Test
    public void testTotal() {
        currencyVO.setTotal(BigDecimal.TEN);
        assertEquals("Currency total.", BigDecimal.TEN, currencyVO.getTotal());
    }

    @Test
    public void testRemovable() {
        currencyVO.setRemovable(true);
        assertEquals("Currency is removable.", true, currencyVO.isRemovable());
    }

    @Test
    public void testHash() {
        currencyVO.setCurrencyId(15);
        CurrencyVO other = new CurrencyVO();
        other.setCurrencyId(15);
        assertEquals("Hash code.", currencyVO.hashCode(), other.hashCode());
        assertEquals("To String.", currencyVO.toString(), other.toString());
        assertTrue("Equals.", currencyVO.equals(currencyVO));
        assertTrue("Equals.", currencyVO.equals(other));
        assertFalse("Equals.", currencyVO.equals(null));
        assertFalse("Equals.", currencyVO.equals(new Object()));
    }
}