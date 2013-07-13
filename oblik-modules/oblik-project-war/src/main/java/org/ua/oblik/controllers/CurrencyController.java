package org.ua.oblik.controllers;

import java.util.List;
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
import org.ua.oblik.controllers.beans.CurrencyBean;
import org.ua.oblik.controllers.utils.ValidationErrorLoger;
import org.ua.oblik.controllers.validators.CurrencyValidator;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class CurrencyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyController.class);

    private static final String CURRENCY_BEAN = "currencyBean";

    private static final String CURRENCY_LIST = "currencyList";
    
    @Autowired
    private CurrencyValidator currencyValidator;
    
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(value = "/currency/list", method = RequestMethod.GET)
    public String listCurrencies(final Model model) {
        LOGGER.debug("Showing currency list.");
        List<CurrencyVO> list = currencyService.getCurrencies();
        model.addAttribute(CURRENCY_LIST, list);
        return "loaded/currencies";
    }

    @RequestMapping(value = "/currency/edit", method = RequestMethod.GET)
    public String editCurrency(final Model model,
            final @RequestParam(value = "currencyId", required = false) Integer currencyId) {
        LOGGER.debug("Editing currency, id: " + currencyId + ".");
        CurrencyVO currency = currencyId == null 
                ? new CurrencyVO()
                : currencyService.getCurrency(currencyId);
        CurrencyBean currencyBean = convert(currency);
        model.addAttribute(CURRENCY_BEAN, currencyBean);
        return "loaded/currency";
    }

    @RequestMapping(value = "/currency/edit", method = RequestMethod.POST)
    public String saveCurrency(final Model model, final @ModelAttribute(CURRENCY_BEAN) @Valid CurrencyBean currencyBean,
            BindingResult bindingResult) {
        LOGGER.debug("Saving currency, id: " + currencyBean.getCurrencyId() + ".");
        currencyValidator.validate(currencyBean, bindingResult);
        if (bindingResult.hasErrors()) {
            ValidationErrorLoger.debug(bindingResult);
        } else {
            CurrencyVO cvo = convert(currencyBean);
            currencyService.save(cvo);
        }
        return "loaded/currency";
    }

    private CurrencyVO convert(CurrencyBean currencyBean) {
        CurrencyVO result = new CurrencyVO();
        result.setCurrencyId(currencyBean.getCurrencyId());
        result.setRate(currencyBean.getRate());
        result.setSymbol(currencyBean.getSymbol());
        return result;
    }

    private CurrencyBean convert(CurrencyVO cvo) {
        CurrencyBean result = new CurrencyBean();
        result.setCurrencyId(cvo.getCurrencyId());
        result.setRate(cvo.getRate());
        result.setSymbol(cvo.getSymbol());
        return result;
    }
}
