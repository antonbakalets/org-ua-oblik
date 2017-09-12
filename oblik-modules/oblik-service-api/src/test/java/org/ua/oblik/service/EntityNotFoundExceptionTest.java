package org.ua.oblik.service;

import org.junit.Assert;
import org.junit.Test;

public class EntityNotFoundExceptionTest {

    private static final String ANY_MESSAGE = "Any message.";

    @Test
    public void testCreate() throws Exception {
        EntityNotFoundException entityNotFoundException = new EntityNotFoundException(ANY_MESSAGE);
        Assert.assertEquals("Message is stored.", ANY_MESSAGE, entityNotFoundException.getMessage());
    }
}