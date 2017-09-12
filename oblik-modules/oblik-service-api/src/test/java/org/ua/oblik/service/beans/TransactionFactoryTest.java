package org.ua.oblik.service.beans;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionFactoryTest {

    private TransactionFactory transactionFactory;

    @Before
    public void setUp() {
        transactionFactory = new TransactionFactory();
    }

    @Test
    public void testCreate() {
        TransactionVO transactionVO = transactionFactory.create(TransactionType.EXPENSE);
        assertEquals("Transaction types should be equal.", TransactionType.EXPENSE, transactionVO.getType());
    }
}