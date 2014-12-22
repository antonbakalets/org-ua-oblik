package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.number.NumberFormatter;

/**
 *
 */
abstract class AbstractHelper {

    @Autowired
    @Qualifier(value = "decimalNumberFormatter")
    private NumberFormatter decimalFormatter;

    protected String formatDecimal(BigDecimal value, Locale locale) {
        BigDecimal toFormat = value == null ? BigDecimal.ZERO : value;
        return decimalFormatter.print(toFormat, locale);
    }
}
