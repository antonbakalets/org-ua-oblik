package org.ua.oblik.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ua.oblik.controllers.beans.FormActionBean;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.TransactionService;
import org.ua.oblik.service.beans.TransactionVO;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class TransactionController {
    
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    
    private static final String TRANSACTIONS = "transaction_list";
    
    @Autowired
    private TransactionService trannsationService;
    
    @Autowired
    private AccountService accountService;
    
    @RequestMapping("/transaction/list")
    public String transactions(final Model model) {
        LOG.debug("transactions");
        List<TransactionVO> tempList = trannsationService.getTransactions();
        List<FormActionBean> list = convertList(tempList);
        model.addAttribute(TRANSACTIONS, list);
        return "loaded/transactions";
    }
    
    private FormActionBean convert(TransactionVO tvo) {
        FormActionBean result = new FormActionBean();
        result.setDate(tvo.getDate());
        result.setFirstAccount(tvo.getFirstAccount());
        result.setFirstAmmount(tvo.getFirstAmmount());
        result.setTxId(tvo.getTxId());
        result.setNote(tvo.getNote());
        result.setSecondAccount(tvo.getSecondAccount());
        result.setSecondAmmount(tvo.getSecondAmmount());
        result.setType(tvo.getType());
        return result;
    }
    
    private List<FormActionBean> convertList (List<TransactionVO> list) {
    	List<FormActionBean> result = new ArrayList<FormActionBean>();
    	for(TransactionVO temp :list) {
    		result.add(convert(temp));
    	}
    	return result;
    }
    
    
    
}
