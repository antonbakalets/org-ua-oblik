package org.ua.oblik.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class TotalController {
    
    private static final Logger LOG = LoggerFactory.getLogger(TotalController.class);
    
    private static final String ASSETS_ACCOUNTS   = "assetsAccounts";
    
    private static final String TOTAL = "total";
    
    private static final String TOTAL_BY_CURRENCIES = "totalByCur";
    
    private static final String DEFAULT_CURRENCY = "defCurrency";
    
    
    @Autowired
    private CurrencyService currencyService;
    
    @Autowired
    private AccountService accountService;
    
    @RequestMapping("/total/currecy")
    public String totalCurrency(final Model model) {
        LOG.debug("Loading total by currency...");
        Map<CurrencyVO, BigDecimal> map = accountService.totalAssetsByCurrency();
        model.addAttribute(TOTAL_BY_CURRENCIES,map);
        return "loaded/total-by-currency";
    }
    
    @RequestMapping("/total/account")
    public String totalAccount(final Model model) {
        LOG.debug("Loading total by account...");	
        List<AccountVO> list = accountService.getAssetsAccounts();
        model.addAttribute(ASSETS_ACCOUNTS, list);
        return "loaded/total-by-account";
    }
    
    @RequestMapping("/total/ammount")
    public String totalAmmount(final Model model) {
        BigDecimal total = accountService.totalAssets();
        LOG.debug("Loading total ammount in default currency: " + total);
        CurrencyVO cvo = currencyService.getDefaultCurrency();
        model.addAttribute(DEFAULT_CURRENCY, cvo);
        model.addAttribute(TOTAL,total);
        return "loaded/totalAmmount";
    }
}
