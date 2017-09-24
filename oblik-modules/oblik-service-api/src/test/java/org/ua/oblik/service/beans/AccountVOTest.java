package org.ua.oblik.service.beans;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountVOTest {

    private AccountVO accountVO;

    @Before
    public void setUp() {
        accountVO = new AccountVO();
    }

    @Test
    public void testAccountId() {
        accountVO.setAccountId(15);
        assertEquals("Account id.", Integer.valueOf(15), accountVO.getAccountId());
    }

    @Test
    public void testName() {
        accountVO.setName("Name.");
        assertEquals("Account name.", "Name.", accountVO.getName());
    }

    @Test
    public void testCurrencyId() {
        accountVO.setCurrencyId(15);
        assertEquals("Currency id.", Integer.valueOf(15), accountVO.getCurrencyId());
    }

    @Test
    public void testCurrencySymbol() {
        accountVO.setCurrencySymbol("Sym");
        assertEquals("Symbol.", "Sym", accountVO.getCurrencySymbol());
    }

    @Test
    public void testAmount() {
        accountVO.setAmount(BigDecimal.TEN);
        assertEquals("Amount.", BigDecimal.TEN, accountVO.getAmount());
    }

    @Test
    public void testType() {
        accountVO.setType(AccountVOType.ASSETS);
        assertEquals("Account type.", AccountVOType.ASSETS, accountVO.getType());
    }

    @Test
    public void testRemovable() {
        accountVO.setRemovable(true);
        assertEquals("Is removable.", true, accountVO.isRemovable());
    }

    @Test
    public void testHash() throws Exception {
        accountVO.setAccountId(15);
        AccountVO other = new AccountVO();
        other.setAccountId(15);
        assertEquals("Hash code.", accountVO.hashCode(), other.hashCode());
        assertEquals("To String.", accountVO.toString(), other.toString());
        assertTrue("Equals.", accountVO.equals(accountVO));
        assertTrue("Equals.", accountVO.equals(other));
        assertFalse("Equals.", accountVO.equals(null));
        assertFalse("Equals.", accountVO.equals(new Object()));
    }
}