package org.ua.oblik.controllers;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.number.NumberFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    private static final String DEFAULT_CURRENCY_EXISTS = "defaultCurrencyExists";

    private static final String DEFAULT_CURRENCY_SYMBOL = "defaultCurrencySymbol";

    private static final String DEFAULT_CURRENCY_TOTAL = "defaultCurrencyTotal";

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    @Qualifier(value = "decimalNumberFormatter")
    private NumberFormatter decimalFormatter;

    @RequestMapping("/main")
    public String welcome(final Locale locale, final Model model) {
        LOG.debug("main");
        final boolean defaultExists = currencyService.isDefaultExists();
        model.addAttribute(DEFAULT_CURRENCY_EXISTS, defaultExists);
        if (defaultExists) {
            final CurrencyVO defaultCurrency = currencyService.getDefaultCurrency();
            model.addAttribute(DEFAULT_CURRENCY_SYMBOL, defaultCurrency.getSymbol());
            model.addAttribute(DEFAULT_CURRENCY_TOTAL, 
                    decimalFormatter.print(accountService.totalAssets(), locale));
        }
        return "layout";
    }
}
