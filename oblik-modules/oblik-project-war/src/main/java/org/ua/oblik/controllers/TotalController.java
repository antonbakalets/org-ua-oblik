package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ua.oblik.controllers.beans.AccountListBean;
import org.ua.oblik.service.TotalService;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class TotalController {

    private static final Logger LOG = LoggerFactory.getLogger(TotalController.class);

    private static final String ASSETS_ACCOUNTS = "assetsAccounts";

    @Autowired
    private AccountFacade accountFacade;

    @Autowired
    private TotalService totalService;

    @RequestMapping("/total/account")
    public String totalAccount(final Model model, final Locale locale) {
        LOG.debug("Loading total by account...");
        List<AccountListBean> list = accountFacade.getAssetsAccounts(locale);
        model.addAttribute(ASSETS_ACCOUNTS, list);
        return "loaded/total-by-account";
    }

    @RequestMapping("/total/ammount")
    @ResponseBody
    public BigDecimal totalAmount() {
        BigDecimal total = totalService.getDefaultCurrencyTotal();
        LOG.debug("Loading total amount in default currency: " + total);
        return total;
    }
}
