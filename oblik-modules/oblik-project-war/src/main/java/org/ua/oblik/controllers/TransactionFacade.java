package org.ua.oblik.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.controllers.beans.TransactionBean;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.TransactionService;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.TransactionVO;

/**
 *
 * @author Anton Bakalets
 */
class TransactionFacade extends AbstractHelper {

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private AccountService accountService;

    TransactionBean getTransaction(Integer transactionId, Locale locale) {
        TransactionVO tvo = transactionService.getTransaction(transactionId);
        TransactionBean transactionBean = convert(tvo, locale);
        return transactionBean;
    }
    
    List<TransactionBean> getTransactions(Date now, Locale locale) {
        List<TransactionVO> tempList = transactionService.getTransactions(now);
        List<TransactionBean> list = convertList(tempList, locale);
        return list;
    }
    
    private List<TransactionBean> convertList(List<TransactionVO> list, Locale locale) {
        List<TransactionBean> result = new ArrayList<>();
        for (TransactionVO temp : list) {
            result.add(convert(temp, locale));
        }
        return result;
    }

    private TransactionBean convert(TransactionVO tvo, Locale locale) {
        TransactionBean result = new TransactionBean();
        result.setDate(formatLongDate(tvo.getDate(), locale));
        final AccountVO firstAccount = accountService.getAccount(tvo.getFirstAccount());
        result.setFirstAccountName(firstAccount.getName());
        result.setFirstSymbol(firstAccount.getCurrencySymbol());
        result.setFirstAmount(formatDecimal(tvo.getFirstAmmount(), locale));
        result.setTransactionId(tvo.getTxId());
        result.setNote(tvo.getNote());
        final AccountVO secondAccount = accountService.getAccount(tvo.getSecondAccount());
        result.setSecondAccountName(secondAccount.getName());
        if (tvo.getSecondAmmount() != null) {
            result.setSecondAmount(formatDecimal(tvo.getSecondAmmount(), locale));
        }
        result.setSecondSymbol(secondAccount.getCurrencySymbol());
        result.setType(tvo.getType());
        return result;
    }

    Map<String, List<TransactionBean>> getTransactionMap(Date now, Locale locale) {
        final Map<String, List<TransactionBean>> result = new HashMap<>();
        final List<TransactionBean> list = getTransactions(now, locale);
        for (TransactionBean converted : list) {
            List<TransactionBean> dayList = result.get(converted.getDate());
            if (dayList == null) {
                dayList = new ArrayList<>();
                dayList.add(converted);
                result.put(converted.getDate(), dayList);
            } else {
                dayList.add(converted);
            }            
        }
        return result;
    }

    public void delete(Integer transactionId) {
        transactionService.delete(transactionId);
    }
}
