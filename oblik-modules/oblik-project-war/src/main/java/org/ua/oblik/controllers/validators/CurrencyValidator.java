package org.ua.oblik.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.ua.oblik.controllers.beans.CurrencyBean;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.CurrencyVO;

public class CurrencyValidator implements Validator {

    @Autowired
    private CurrencyService currencyService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CurrencyBean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CurrencyBean bean = (CurrencyBean) target;
        final Integer currencyId = bean.getCurrencyId();

        if (currencyId != null) {
            final CurrencyVO old = currencyService.getCurrency(currencyId);
            if (!old.getSymbol().equals(bean.getSymbol())) {
                if (currencyService.isSymbolExists(bean.getSymbol())) {
                    errors.rejectValue("symbol", "error.currency.symbol.exists");
                }
            }
        } else {
            if (currencyService.isSymbolExists(bean.getSymbol())) {
                errors.rejectValue("symbol", "error.currency.symbol.exists");
            }
        }
    }
}
