package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.ua.oblik.controllers.beans.CurrencyEditBean;
import org.ua.oblik.controllers.beans.CurrencyListBean;
import org.ua.oblik.controllers.utils.ValidationErrorLoger;
import org.ua.oblik.controllers.validators.CurrencyValidator;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class CurrencyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyController.class);

    private static final String CURRENCY_BEAN = "currencyBean";

    private static final String CURRENCY_LIST = "currencyList";

    private static final String SAVING_DEFAULT_CURRENCY = "savingDefaultCurrency";

    private static final String DEFAULT_CURRENCY_SYMBOL = "defaultCurrencySymbol";
    
    @Autowired
    private CurrencyValidator currencyValidator;

    @Autowired
    private CurrencyFacade currencyHelper;

    @RequestMapping(value = "/currency/list", method = RequestMethod.GET)
    public String listCurrencies(final Model model, final Locale locale) {
        LOGGER.debug("Showing currency list.");
        List<CurrencyListBean> beanList = currencyHelper.getCurrencies(locale);
        model.addAttribute(CURRENCY_LIST, beanList);
        if (!beanList.isEmpty()) {
            model.addAttribute(DEFAULT_CURRENCY_SYMBOL, currencyHelper.getDefaultCurrencySymbol());
        }
        return "loaded/currencies";
    }

    @RequestMapping(value = "/currency/edit", method = RequestMethod.GET)
    public String editCurrency(final HttpSession session, final Model model,
            @RequestParam(value = "currencyId") final Optional<Integer> currencyId) {
        LOGGER.debug("Editing currency, id: " + currencyId + ".");
        CurrencyEditBean currencyEditBean = currencyHelper.createCurrencyBean(currencyId);
        currencyEditBean.setOldSymbol(currencyEditBean.getSymbol());
        // TODO convert to annotations?
        session.setAttribute(SAVING_DEFAULT_CURRENCY, currencyEditBean.getDefaultRate());
        model.addAttribute(CURRENCY_BEAN, currencyEditBean);
        return "loaded/currency";
    }

    @RequestMapping(value = "/currency/edit", method = RequestMethod.POST)
    public String saveCurrency(final HttpSession session,
            @ModelAttribute(CURRENCY_BEAN) @Valid final CurrencyEditBean currencyEditBean,
            final BindingResult bindingResult) {
        LOGGER.debug("Saving currency, id: " + currencyEditBean.getCurrencyId() + ".");
        if ((Boolean) session.getAttribute(SAVING_DEFAULT_CURRENCY)) {
            currencyEditBean.setDefaultRate(Boolean.TRUE);
            currencyEditBean.setRate(BigDecimal.ONE);
        }
        currencyValidator.validate(currencyEditBean, bindingResult);
        if (bindingResult.hasErrors()) {
            ValidationErrorLoger.debug(bindingResult);
        } else {
            currencyHelper.save(currencyEditBean);
        }
        return "loaded/currency";
    }

//    @RequestMapping(value = "/currency/delete", method = RequestMethod.GET)
//    @ResponseBody
//    public String confirmRemoveCurrency(@RequestParam(value = "currencyId", required = false) final Integer currencyId,
//                                        HttpSession session) {
//        LOGGER.debug("Generating confirmation to remove currency, id: " + currencyId + ".");
//        String confirmation = UUID.randomUUID().toString();
//        session.setAttribute(confirmation, currencyId);
//        return confirmation;
//    }

    @RequestMapping(value = "/currency/delete", method = RequestMethod.GET)
    @ResponseBody
    public Boolean removeCurrency(@RequestParam(value = "currencyId", required = false) final Integer currencyId) {
        LOGGER.debug("Removing currency, id: " + currencyId + ".");
        Boolean success;
        try {
            currencyHelper.remove(currencyId);
            success = true;
        } catch (Exception e) {
            success = false;
        }
        return success;
    }
}
