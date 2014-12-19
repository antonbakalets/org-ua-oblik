package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.number.NumberFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
public class TransactionController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    private static final String TRANSACTIONS = "transaction_list";
    
    private static final String TRANSACTION_MAP = "transaction_map";

    private static final String TRANSACTION_BEAN = "transaction";
    
    private static final String MONTH_ARRAY = "monthArray";

    @Autowired
    @Qualifier(value = "decimalNumberFormatter")
    private NumberFormatter decimalFormatter;
    
    @Autowired
    @Qualifier(value = "monthDateFormat")
    private SimpleDateFormat monthDateFormat;
    
    @Autowired
    @Qualifier(value = "monthDateEditor")
    private CustomDateEditor monthDateEditor;
    
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/transaction/list")
    public String transactions(final Locale locale, final Model model,
            @RequestParam(value = "month", required = false) final Date month) {
        LOG.debug("Listing transactions on: " + month);
        final Date now = month == null ? new Date() : month;
        List<TransactionVO> tempList = transactionService.getTransactions(now);
        List<TransactionBean> list = convertList(tempList, locale);
        model.addAttribute(TRANSACTIONS, list);
        model.addAttribute(TRANSACTION_MAP, convertToMap(tempList, locale));
        model.addAttribute(MONTH_ARRAY, threeMonths(now));
        return "loaded/transactions";
    }

    @RequestMapping(value = "/transaction/delete", method = RequestMethod.GET)
    public String deleteTransaction(final Model model, final Locale locale,
            @RequestParam(value = "transactionId", required = false) final Integer transactionId) {
        LOG.debug("Delete transaction, id: " + transactionId + ".");
        TransactionVO tvo = transactionService.getTransaction(transactionId);
        // TODO Locale locale = LocaleContextHolder.getLocale();
        TransactionBean transactionBean = convert(tvo, Locale.CANADA_FRENCH);
        model.addAttribute(TRANSACTION_BEAN, transactionBean);
        return "loaded/transaction";
    }

    @RequestMapping(value = "/transaction/delete", method = RequestMethod.POST)
    public String deleteTransaction(final Model model,
            @ModelAttribute(TRANSACTION_BEAN) @Valid final TransactionBean transactionBean,
            final BindingResult bindingResult) {
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

    protected String formatDecimal(BigDecimal value, Locale locale) {
        BigDecimal toFormat = value == null ? BigDecimal.ZERO : value;
        return decimalFormatter.print(toFormat, locale);
    }
    
    private List<TransactionBean> convertList(List<TransactionVO> list, Locale locale) {
        List<TransactionBean> result = new ArrayList<>();
        for (TransactionVO temp : list) {
            result.add(convert(temp, locale));
        }
        return result;
    }
    
    private Map<Date, List<TransactionBean>> convertToMap(List<TransactionVO> list, Locale locale) {
        Map<Date, List<TransactionBean>> result = new HashMap<>();
        for (TransactionVO elem : list) {
            TransactionBean converted = convert(elem, locale);
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
    
    @InitBinder
    public void setPropertyEditors(final WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, monthDateEditor);
    }

    private String[] threeMonths(Date date) {
        String[] result = new String[3];
        result[0] = monthDateFormat.format(addMonth(date, -1));
        result[1] = monthDateFormat.format(date);
        result[2] = monthDateFormat.format(addMonth(date, +1));
        return result;
    }

    // TODO move to utility class
    public static Date addMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }
}
