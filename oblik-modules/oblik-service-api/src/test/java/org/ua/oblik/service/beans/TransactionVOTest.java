package org.ua.oblik.service.beans;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class TransactionVOTest {

    private TransactionVO transactionVO;

    @Before
    public void setUp() {
        transactionVO = new TransactionVO();
    }

    @Test
    public void testTxId() {
        transactionVO.setTxId(15);
        assertEquals("Transaction id.", Integer.valueOf(15), transactionVO.getTxId());
    }

    @Test
    public void testType() {
        transactionVO.setType(TransactionType.EXPENSE);
        assertEquals("Transaction type.", TransactionType.EXPENSE, transactionVO.getType());
    }

    @Test
    public void testFirstAccount() {
        transactionVO.setFirstAccount(15);
        assertEquals("Transaction first account.", Integer.valueOf(15), transactionVO.getFirstAccount());
    }

    @Test
    public void testFirstAmount() {
        transactionVO.setFirstAmount(BigDecimal.TEN);
        assertEquals("Transaction first amount.", BigDecimal.TEN, transactionVO.getFirstAmount());
    }

    @Test
    public void testDate() {
        Date now = new Date();
        transactionVO.setDate(now);
        assertEquals("Transaction date.", now, transactionVO.getDate());
    }

    @Test
    public void testNote() {
        transactionVO.setNote("A note.");
        assertEquals("Transaction note.", "A note.", transactionVO.getNote());
    }

    @Test
    public void testSecondAccount() {
        transactionVO.setSecondAccount(15);
        assertEquals("Transaction second account.", Integer.valueOf(15), transactionVO.getSecondAccount());
    }

    @Test
    public void testSecondAmount() {
        transactionVO.setSecondAmount(BigDecimal.TEN);
        assertEquals("Transaction second amount.", BigDecimal.TEN, transactionVO.getSecondAmount());
    }

    @Test
    public void testHash() {
        transactionVO.setTxId(15);
        TransactionVO other = new TransactionVO();
        other.setTxId(15);
        assertEquals("Hash code.", transactionVO.hashCode(), other.hashCode());
        assertEquals("To String.", transactionVO.toString(), other.toString());
        assertTrue("Equals.", transactionVO.equals(transactionVO));
        assertTrue("Equals.", transactionVO.equals(other));
        assertFalse("Equals.", transactionVO.equals(null));
        assertFalse("Equals.", transactionVO.equals(new Object()));
    }
}