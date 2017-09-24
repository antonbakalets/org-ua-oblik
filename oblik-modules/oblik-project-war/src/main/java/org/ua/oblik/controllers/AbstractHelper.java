package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.NumberStyleFormatter;

/**
 *
 */
abstract class AbstractHelper {

    @Autowired
    @Qualifier(value = "decimalNumberFormatter")
    private NumberStyleFormatter decimalFormatter;

    @Autowired
    @Qualifier(value = "longDateFormatter")
    private DateFormatter longDateFormatter;
    
    protected String formatDecimal(BigDecimal value, Locale locale) {
        BigDecimal toFormat = value == null ? BigDecimal.ZERO : value;
        return decimalFormatter.print(toFormat, locale);
    }
    
    protected String formatLongDate(Date value, Locale locale) {
        return longDateFormatter.print(value, locale);
    }
}
