package org.ua.oblik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BudgetServiceImpl implements BudgetService {

    private AccountService accountService;

    private CurrencyService currencyService;

    private TotalService totalService;

    private TransactionService transactionService;

    @Override
    public BudgetVO getBudget() {
        BudgetVO budgetVO = new BudgetVO();
        budgetVO.setTotal(totalService.getDefaultCurrencyTotal());

        List<AccountVO> accounts = accountService.getAccounts(AccountCriteria.EMPTY_CRITERIA);
        budgetVO.setAccounts(accounts.stream().collect(Collectors
                .toMap(AccountVO::getAccountId, Function.identity())));

        List<CurrencyVO> currencies = currencyService.getCurrencies();
        budgetVO.setCurrencies(currencies.stream().collect(Collectors
                .toMap(CurrencyVO::getCurrencyId, Function.identity())));

        List<TransactionVO> transactions = transactionService.getTransactions();
        budgetVO.setTransactions(transactions.stream().collect(Collectors
                .toMap(TransactionVO::getTxId, Function.identity())));
        return budgetVO;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Autowired
    public void setTotalService(TotalService totalService) {
        this.totalService = totalService;
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
