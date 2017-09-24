package org.ua.oblik.service;

import org.junit.Assert;
import org.junit.Test;

public class BusinessConstraintExceptionTest {

    private static final String ANY_MESSAGE = "Any message.";

    @Test
    public void testCreate() throws Exception {
        BusinessConstraintException businessConstraintException = new BusinessConstraintException(ANY_MESSAGE);
        Assert.assertEquals("Message is stored.", ANY_MESSAGE, businessConstraintException.getMessage());
    }

}