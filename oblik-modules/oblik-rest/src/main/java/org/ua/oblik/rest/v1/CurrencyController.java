package org.ua.oblik.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.BudgetChange;
import org.ua.oblik.service.beans.CurrencyVO;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private CurrencyService currencyService;

    @PostMapping
    @ResponseBody
    public BudgetChange postCurrency(@RequestBody CurrencyVO currency) {
        currencyService.save(currency);
        return new BudgetChange();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public BudgetChange putCurrency(@PathVariable Integer id) {
        CurrencyVO currency = currencyService.getCurrency(id);
        currencyService.save(currency);
        return new BudgetChange();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public BudgetChange deleteCurrency(@PathVariable Integer id) {
        currencyService.remove(id);
        return new BudgetChange();
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
}
