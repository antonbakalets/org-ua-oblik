package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.number.NumberFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ua.oblik.controllers.beans.CurrencyEditBean;
import org.ua.oblik.controllers.beans.CurrencyListBean;
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

    private static final String SAVING_DEFAULT_CURRENCY = "savingDefaultCurrency";

    private static final String DEFAULT_CURRENCY_SYMBOL = "defaultCurrencySymbol";
    
    @Autowired
    private CurrencyValidator currencyValidator;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    @Qualifier(value = "decimalNumberFormatter")
    private NumberFormatter decimalFormatter;

    @RequestMapping(value = "/currency/list", method = RequestMethod.GET)
    public String listCurrencies(final Model model, final Locale locale) {
        LOGGER.debug("Showing currency list.");
        List<CurrencyVO> list = currencyService.getCurrencies();
        List<CurrencyListBean> beanList = convert(list, locale);
        model.addAttribute(CURRENCY_LIST, beanList);
        final CurrencyVO defaultCurrency = currencyService.getDefaultCurrency();
        model.addAttribute(DEFAULT_CURRENCY_SYMBOL, defaultCurrency.getSymbol());
        return "loaded/currencies";
    }

    @RequestMapping(value = "/currency/edit", method = RequestMethod.GET)
    public String editCurrency(final HttpSession session, final Model model,
            @RequestParam(value = "currencyId", required = false) final Integer currencyId) {
        LOGGER.debug("Editing currency, id: " + currencyId + ".");
        CurrencyEditBean currencyEditBean = createCurrencyBean(currencyId);
        currencyEditBean.setOldSymbol(currencyEditBean.getSymbol());
        // TODO convert to annotations?
        session.setAttribute(SAVING_DEFAULT_CURRENCY, Boolean.valueOf(currencyEditBean.getDefaultRate()));
        model.addAttribute(CURRENCY_BEAN, currencyEditBean);
        return "loaded/currency";
    }

    @RequestMapping(value = "/currency/edit", method = RequestMethod.POST)
    public String saveCurrency(final HttpSession session, final Model model,
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
            CurrencyVO cvo = convert(currencyEditBean);
            currencyService.save(cvo);
        }
        return "loaded/currency";
    }

    private List<CurrencyListBean> convert(List<CurrencyVO> list, Locale locale) {
        List<CurrencyListBean> result = new ArrayList<>();
        for (CurrencyVO vo : list) {
            result.add(convertToListBean(vo, locale));
        }
        return result;
    }

    private CurrencyListBean convertToListBean(CurrencyVO vo, Locale locale) {
        CurrencyListBean result = new CurrencyListBean();
        result.setCurrencyId(vo.getCurrencyId());
        result.setTotal(formatDecimal(vo.getTotal(), locale));
        result.setRate(formatDecimal(vo.getRate(), locale));
        result.setSymbol(vo.getSymbol());
        result.setDefaultRate(vo.getDefaultRate());

        return result;
    }

    private CurrencyVO convert(CurrencyEditBean currencyEditBean) {
        CurrencyVO result = new CurrencyVO();
        result.setCurrencyId(currencyEditBean.getCurrencyId());
        result.setRate(currencyEditBean.getRate());
        result.setSymbol(currencyEditBean.getSymbol());
        result.setDefaultRate(currencyEditBean.getDefaultRate());
        return result;
    }

    private CurrencyEditBean convert(CurrencyVO cvo) {
        CurrencyEditBean result = new CurrencyEditBean();
        result.setCurrencyId(cvo.getCurrencyId());
        result.setRate(cvo.getRate());
        result.setSymbol(cvo.getSymbol());
        result.setDefaultRate(cvo.getDefaultRate());
        return result;
    }

    private CurrencyEditBean createCurrencyBean(final Integer currencyId) {
        CurrencyVO result;
        if (currencyId == null) {
            result = currencyService.createCurrency();
        } else {
            result = currencyService.getCurrency(currencyId);
        }
        return convert(result);
    }

    protected String formatDecimal(BigDecimal value, Locale locale) {
        BigDecimal toFormat = value == null ? BigDecimal.ZERO : value;
        return decimalFormatter.print(toFormat, locale);
    }
}
