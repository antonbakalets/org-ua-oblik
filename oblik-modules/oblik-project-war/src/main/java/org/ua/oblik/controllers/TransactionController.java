package org.ua.oblik.controllers;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ua.oblik.controllers.beans.TransactionBean;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.TransactionService;
import org.ua.oblik.service.beans.TransactionVO;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class TransactionController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    private static final String TRANSACTIONS = "transaction_list";
    
    private static final String TRANSACTION_BEAN = "transaction";
    
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/transaction/list")
    public String transactions(final Locale locale, final Model model) {
        LOG.debug("transactions");
        List<TransactionVO> tempList = transactionService.getTransactions();
        List<TransactionBean> list = convertList(tempList, locale);
        model.addAttribute(TRANSACTIONS, list);
        return "loaded/transactions";
    }
    
    @RequestMapping(value = "/transaction/delete", method = RequestMethod.GET)
    public String deleteTransaction(final Model model,
            final @RequestParam(value = "transactionId", required = false) Integer transactionId) {
        LOG.debug("Delete transaction, id: " + transactionId + ".");
        TransactionVO tvo =  transactionService.getTransaction(transactionId);
        // TODO
        TransactionBean transactionBean = convert(tvo, Locale.CANADA_FRENCH);
        model.addAttribute(TRANSACTION_BEAN, transactionBean);
        return "loaded/transaction";
    }
    
    @RequestMapping(value = "/transaction/delete", method = RequestMethod.POST)
    public String deleteTransaction(final Model model, 
            final @ModelAttribute(TRANSACTION_BEAN) @Valid TransactionBean transactionBean,
            BindingResult bindingResult) {
        LOG.debug("Removing transaction, id: " + transactionBean.getTransactionId() + ".");
        transactionService.delete(transactionBean.getTransactionId());
        return "loaded/transaction";
    }

    private TransactionBean convert(TransactionVO tvo, Locale locale) {
        TransactionBean result = new TransactionBean();
        result.setDate(tvo.getDate());
        result.setFirstAccount(accountService.getAccount(tvo.getFirstAccount()));
        result.setFirstAmmount(formatDecimal(tvo.getFirstAmmount(), locale));
        result.setTransactionId(tvo.getTxId());
        result.setNote(tvo.getNote());
        result.setSecondAccount(accountService.getAccount(tvo.getSecondAccount()));
        if (tvo.getSecondAmmount() != null) {
            result.setSecondAmmount(formatDecimal(tvo.getSecondAmmount(), locale));
        }
        result.setType(tvo.getType());
        return result;
    }

    private List<TransactionBean> convertList(List<TransactionVO> list, Locale locale) {
        List<TransactionBean> result = new ArrayList<TransactionBean>();
        for (TransactionVO temp : list) {
            result.add(convert(temp, locale));
        }
        return result;
    }
}
