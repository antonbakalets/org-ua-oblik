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
    private NumberFormatter decimalFormatter;

    @Autowired
    @Qualifier(value = "bigDecimalEditor")
    private CustomNumberEditor bigDecimalEditor;

    @Autowired
    @Qualifier(value = "longDateEditor")
    private CustomDateEditor longDateEditor;

    public NumberFormatter getDecimalFormatter() {
        return decimalFormatter;
    }

    public CustomNumberEditor getBigDecimalEditor() {
        return bigDecimalEditor;
    }

    public CustomDateEditor getLongDateEditor() {
        return longDateEditor;
    }

    protected String formatDecimal(BigDecimal value, Locale locale) {
        BigDecimal toFormat = value == null ? BigDecimal.ZERO : value;
        return getDecimalFormatter().print(toFormat, locale);
    }
}
