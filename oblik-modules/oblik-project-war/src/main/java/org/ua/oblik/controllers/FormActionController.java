package org.ua.oblik.controllers;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ua.oblik.controllers.beans.FormActionBean;
import org.ua.oblik.service.beans.TransactionVO;
import org.ua.oblik.service.beans.TransactionFactory;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.controllers.utils.TransactionTypePropertyEditor;
import org.ua.oblik.controllers.utils.ValidationErrorLoger;
import org.ua.oblik.controllers.validators.FormActionValidator;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.TransactionService;
import org.ua.oblik.service.beans.AccountVO;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class FormActionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormActionController.class);

    private static final String ACCOUNT_FROM_ITEMS = "accountFromItems";

    private static final String ACCOUNT_TO_ITEMS = "accountToItems";

    @Autowired
    private TransactionFactory transactionFactory;

    @Autowired
    private TransactionTypePropertyEditor transactionTypePropertyEditor;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FormActionValidator actionValidator;

    @ModelAttribute
    public void populateModel(final Model model,
            final @RequestParam(value = "type", required = true) TransactionType type) {
        switch (type) {
            case EXPENSE:
                model.addAttribute(ACCOUNT_FROM_ITEMS, accountService.getAssetsAccounts());
                model.addAttribute(ACCOUNT_TO_ITEMS, accountService.getExpenseAccounts());
                break;
            case INCOME:
                model.addAttribute(ACCOUNT_FROM_ITEMS, accountService.getIncomeAccounts());
                model.addAttribute(ACCOUNT_TO_ITEMS, accountService.getAssetsAccounts());
                break;
            case TRANSFER:
                model.addAttribute(ACCOUNT_FROM_ITEMS, accountService.getAssetsAccounts());
                model.addAttribute(ACCOUNT_TO_ITEMS, accountService.getAssetsAccounts());
                break;
        }
    }

    @RequestMapping(value = "/formaction", method = RequestMethod.GET)
    public String formaction(final Model model,
            final @RequestParam(value = "type", required = true) TransactionType type) {
        final FormActionBean transaction = convert(transactionFactory.create(type));
        model.addAttribute("formActionBean", transaction);
        return "loaded/formaction";
    }

    @RequestMapping(value = "/formaction", method = RequestMethod.POST)
    public String submitExpense(final Model model,
            final @ModelAttribute("formActionBean") @Valid FormActionBean bean,
            final BindingResult bindingResult) {
        LOGGER.debug("Saving action: " + bean.getType());
        actionValidator.validate(bean, bindingResult);
        if (bindingResult.hasErrors()) {
            ValidationErrorLoger.debug(bindingResult);
        } else {
            transactionService.save(convert(bean));
            return "redirect:/formaction";
        }
        return "loaded/formaction";
    }

    private TransactionVO convert(FormActionBean bean) {
        TransactionVO result = new TransactionVO();
        result.setDate(bean.getDate());
        result.setFirstAccount(bean.getFirstAccount());
        result.setFirstAmmount(bean.getFirstAmmount());
        result.setTxId(bean.getTxId());
        result.setNote(bean.getNote());
        result.setSecondAccount(bean.getSecondAccount());
        result.setSecondAmmount(bean.getSecondAmmount());
        result.setType(bean.getType());
        return result;
    }

    private FormActionBean convert(TransactionVO tvo) {
        FormActionBean result = new FormActionBean();
        result.setType(tvo.getType());
        return result;
    }

    @InitBinder
    public void setPropertyEditors(final WebDataBinder binder) {
        binder.registerCustomEditor(TransactionType.class, transactionTypePropertyEditor);
    }
}
