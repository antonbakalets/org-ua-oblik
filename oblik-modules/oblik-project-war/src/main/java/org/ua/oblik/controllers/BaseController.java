package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.format.number.NumberFormatter;

/**
 *
 * @author Anton Bakalets
 */
abstract class BaseController {
    
    @Autowired
    @Qualifier(value = "decimalNumberFormatter")
    protected NumberFormatter decimalFormatter;
     
    @Autowired
    @Qualifier(value = "bigDecimalEditor")
    protected CustomNumberEditor bigDecimalEditor;

    @Autowired
    @Qualifier(value = "longDateEditor")
    protected CustomDateEditor longDateEditor;

    protected String formatDecimal(BigDecimal value, Locale locale) {
        if (value == null) {
            throw new NullPointerException("Cannot format given null as a Number.");
        }
        return decimalFormatter.print(value, locale);
    }
    
}
