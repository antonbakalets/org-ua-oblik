package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.ua.oblik.controllers.beans.LoginAction;
import org.ua.oblik.controllers.utils.LoginActionPropertyEditor;
import org.ua.oblik.controllers.utils.TransactionTypePropertyEditor;
import org.ua.oblik.service.beans.TransactionType;

/**
 *
 * @author Anton Bakalets
 */
@ControllerAdvice
public class CentralControllerHandler {

    @Autowired
    @Qualifier(value = "bigDecimalEditor")
    private CustomNumberEditor bigDecimalEditor;

    @Autowired
    @Qualifier(value = "longDateEditor")
    private CustomDateEditor longDateEditor;
    
    @Autowired
    private TransactionTypePropertyEditor transactionTypePropertyEditor;

    @Autowired
    private LoginActionPropertyEditor loginActionPropertyEditor;
    
    @InitBinder
    public void setPropertyEditors(final WebDataBinder binder) {
        binder.registerCustomEditor(TransactionType.class, transactionTypePropertyEditor);
        binder.registerCustomEditor(Date.class, longDateEditor);
        binder.registerCustomEditor(BigDecimal.class, bigDecimalEditor);
        binder.registerCustomEditor(LoginAction.class, loginActionPropertyEditor);
    }
}
