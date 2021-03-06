package org.ua.oblik.rest.v1.dto;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

public class AccountResource extends ResourceSupport {

    private String name;
    private String type;
    private String symbol;
    private BigDecimal amount;
    private Integer currencyId;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }
}
