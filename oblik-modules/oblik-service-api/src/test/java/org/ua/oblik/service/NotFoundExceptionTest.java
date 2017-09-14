package org.ua.oblik.service;

import org.junit.Assert;
import org.junit.Test;

public class NotFoundExceptionTest {

    private static final String ANY_MESSAGE = "Any message.";

    @Test
    public void testCreate() throws Exception {
        NotFoundException notFoundException = new NotFoundException(ANY_MESSAGE);
        Assert.assertEquals("Message is stored.", ANY_MESSAGE, notFoundException.getMessage());
    }
}