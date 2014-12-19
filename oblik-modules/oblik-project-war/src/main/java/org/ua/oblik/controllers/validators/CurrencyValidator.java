package org.ua.oblik.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.ua.oblik.controllers.beans.CurrencyEditBean;
import org.ua.oblik.service.CurrencyService;

public class CurrencyValidator implements Validator {

    @Autowired
    private CurrencyService currencyService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CurrencyEditBean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CurrencyEditBean bean = (CurrencyEditBean) target;
        final Integer currencyId = bean.getCurrencyId();

        if (currencyId != null) {
            if (!bean.getSymbol().equals(bean.getOldSymbol())) {
                validateSymbol(bean.getSymbol(), errors);
            }
        } else {
            validateSymbol(bean.getSymbol(), errors);
        }
    }

    private void validateSymbol(String symbol, Errors errors) {
        if (currencyService.isSymbolExists(symbol)) {
            errors.rejectValue("symbol", "error.currency.symbol.exists");
        }
    }
}
