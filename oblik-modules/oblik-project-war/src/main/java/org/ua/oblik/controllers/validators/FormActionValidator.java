package org.ua.oblik.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.ua.oblik.controllers.beans.FormActionBean;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.beans.AccountVO;

/**
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
        if (bean.getFirstAccount() != null && bean.getSecondAccount() != null) {
            final AccountVO firstAccount = accountService.getAccount(bean.getFirstAccount());
            final AccountVO secondAccount = accountService.getAccount(bean.getSecondAccount());
            switch (bean.getType()) {
                case EXPENSE:
                    validateAsExpense(errors, firstAccount, secondAccount);
                    break;
                case INCOME:
                    validateAsIncome(errors, firstAccount, secondAccount);
                    break;
                case TRANSFER:
                    validateAsTransfer(bean, errors, firstAccount, secondAccount);
                    break;
            }
        }
    }

    private void validateAsExpense(Errors errors, AccountVO firstAccount, AccountVO secondAccount) {
        if (!firstAccount.getCurrencyId().equals(secondAccount.getCurrencyId())) {
            errors.rejectValue("secondAccount", "error.transaction.different.currencies");
        }
    }

    private void validateAsIncome(Errors errors, AccountVO firstAccount, AccountVO secondAccount) {
        if (!firstAccount.getCurrencyId().equals(secondAccount.getCurrencyId())) {
            errors.rejectValue("secondAmount", "error.transaction.different.currencies");
        }
    }

    private void validateAsTransfer(FormActionBean bean, Errors errors, AccountVO firstAccount, AccountVO secondAccount) {
        if (firstAccount.equals(secondAccount)) {
            errors.rejectValue("secondAccount", "error.transaction.transfer.same.account");
        }
        if (!firstAccount.getCurrencyId().equals(secondAccount.getCurrencyId())
                && bean.getSecondAmount() == null) {
            errors.rejectValue("secondAmount", "error.transaction.transfer.second.account");
        }
    }
}
