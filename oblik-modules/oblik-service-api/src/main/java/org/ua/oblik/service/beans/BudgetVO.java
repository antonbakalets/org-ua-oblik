package org.ua.oblik.service.beans;

import java.math.BigDecimal;
import java.util.Map;

public class BudgetVO {

    private BigDecimal total;
    private Map<Integer, CurrencyVO> currencies;
    private Map<Integer, AccountVO> accounts;
    private Map<Integer, TransactionVO> transactions;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Map<Integer, CurrencyVO> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<Integer, CurrencyVO> currencies) {
        this.currencies = currencies;
    }

    public Map<Integer, AccountVO> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<Integer, AccountVO> accounts) {
        this.accounts = accounts;
    }

    public Map<Integer, TransactionVO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<Integer, TransactionVO> transactions) {
        this.transactions = transactions;
    }
}
