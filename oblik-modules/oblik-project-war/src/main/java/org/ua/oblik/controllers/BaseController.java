package org.ua.oblik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 *
 * @author Anton Bakalets
 */
abstract class BaseController {
    
    @Autowired
    @Qualifier(value = "bigDecimalEditor")
    protected CustomNumberEditor bigDecimalEditor;

    @Autowired
    @Qualifier(value = "longDateEditor")
    protected CustomDateEditor longDateEditor;
    
}
