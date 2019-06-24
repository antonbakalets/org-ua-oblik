package org.ua.oblik.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@SqlResultSetMapping(
        name = "currencyTotal",
        entities = @EntityResult(
                entityClass = CurrencyTotalMapping.class,
                fields = {
                        @FieldResult(name = "currId", column = "curr_id"),
                        @FieldResult(name = "symbol", column = "symbol"),
                        @FieldResult(name = "byDefault", column = "by_default"),
                        @FieldResult(name = "rate", column = "rate"),
                        @FieldResult(name = "total", column = "sumtotal")
                })
)
public class CurrencyTotalMapping implements CurrencyTotal {

    @Id
    private Integer currId;
    private String symbol;
    private boolean byDefault;
    private BigDecimal rate;
    private BigDecimal total;

    @Override
    public Integer getCurrId() {
        return currId;
    }

    public void setCurrId(Integer currId) {
        this.currId = currId;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean isByDefault() {
        return byDefault;
    }

    public void setByDefault(boolean byDefault) {
        this.byDefault = byDefault;
    }

    @Override
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
