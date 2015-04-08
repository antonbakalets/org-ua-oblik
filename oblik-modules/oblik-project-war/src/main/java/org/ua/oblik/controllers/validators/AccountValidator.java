package org.ua.oblik.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.ua.oblik.controllers.beans.AccountBean;
import org.ua.oblik.service.AccountService;

public class AccountValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountBean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountBean bean = (AccountBean) target;
        final Integer accountId = bean.getAccountId();

        if (accountId != null) {
            if (!bean.getNewName().equals(bean.getOldName())) {
                validateName(bean.getNewName(), errors);
            }
        } else {
            validateName(bean.getNewName(), errors);
        }
    }

    private void validateName(String name, Errors errors) {
        if (accountService.isNameExists(name)) {
            errors.rejectValue("newName", "error.account.name.exists");
        }
    }
}
