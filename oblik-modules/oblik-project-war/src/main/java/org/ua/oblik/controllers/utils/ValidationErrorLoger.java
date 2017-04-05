package org.ua.oblik.controllers.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Arrays;

/**
 *
 * @author Anton Bakalets
 */
public final class ValidationErrorLoger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationErrorLoger.class);

    private ValidationErrorLoger() {
    }

    public static void debug(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Validation errors, " + bindingResult.getObjectName());
            for (ObjectError error : bindingResult.getAllErrors()) {
                LOGGER.debug("\t{}", Arrays.toString(error.getCodes()));
            }
        }
    }
}
