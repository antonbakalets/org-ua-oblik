package org.ua.oblik.service.beans;

import org.junit.Assert;
import org.junit.Test;
import org.ua.oblik.service.beans.AccountCriteria.Builder;

import static org.junit.Assert.*;

public class AccountCriteriaTest {

    private static final int ID = 15;

    @Test
    public void testExcludeAccountId() throws Exception {
        AccountCriteria criteria = new Builder().excludeAccountId(15).build();
        assertEquals("Excluded account id must match.", Integer.valueOf(ID), criteria.getExcludeAccountId());
    }

    @Test
    public void testCurrencyId() throws Exception {
        AccountCriteria criteria = new Builder().setCurrencyId(15).build();
        assertEquals("Currency id must match.", Integer.valueOf(ID), criteria.getCurrencyId());
    }

    @Test
    public void testType() throws Exception {
        AccountCriteria criteria = new Builder().setType(AccountVOType.EXPENSE).build();
        assertEquals("Account type must match.", AccountVOType.EXPENSE, criteria.getType());
    }
}