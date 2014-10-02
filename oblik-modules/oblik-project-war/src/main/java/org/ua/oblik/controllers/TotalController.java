package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.TotalService;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class TotalController {

    private static final Logger LOG = LoggerFactory.getLogger(TotalController.class);

    private static final String ASSETS_ACCOUNTS = "assetsAccounts";

    @Autowired
    private AccountService accountService;

    @Autowired
    private TotalService totalService;

    @RequestMapping("/total/account")
    public String totalAccount(final Model model) {
        LOG.debug("Loading total by account...");
        List<AccountVO> list = accountService.getAccounts(AccountCriteria.ASSETS_CRITERIA);
        model.addAttribute(ASSETS_ACCOUNTS, list);
        return "loaded/total-by-account";
    }

    @RequestMapping("/total/ammount")
    @ResponseBody
    public BigDecimal totalAmmount(final Model model) {
        BigDecimal total = totalService.getDefaultCurrencyTotal();
        LOG.debug("Loading total ammount in default currency: " + total);
        return total;
    }
}
