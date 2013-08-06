package org.ua.oblik.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.ua.oblik.controllers.beans.FormActionBean;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.beans.AccountVO;

/**
 *
 * @author Anton Bakalets
 */
public class FormActionValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> type) {
        return FormActionBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final FormActionBean bean = (FormActionBean) target;
        switch (bean.getType()) {
            case EXPENSE:
                validateAsExpense(bean, errors);
                break;
            case INCOME:
                validateAsIncome(bean, errors);
                break;
            case TRANSFER:
                validateAsTransfer(bean, errors);
                break;
        }
    }

    private void validateAsExpense(FormActionBean bean, Errors errors) {
        // TODO different messages depending on type
    }

    private void validateAsIncome(FormActionBean bean, Errors errors) {
    }

    private void validateAsTransfer(FormActionBean bean, Errors errors) {
        if (bean.getFirstAccount() != null && bean.getSecondAccount() != null) {
            final AccountVO firstAccount = accountService.getAccount(bean.getFirstAccount());
            final AccountVO secondAccount = accountService.getAccount(bean.getSecondAccount());
            if (!firstAccount.getCurrencyId().equals(secondAccount.getCurrencyId())
                    && bean.getSecondAmmount() == null) {
                errors.rejectValue("secondAmmount", "error.transaction.transfer.second.account");
            }
        }
    }
}
