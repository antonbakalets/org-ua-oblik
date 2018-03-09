package org.ua.oblik.rs.resource;

import java.math.BigDecimal;

public class BudgetRS {

    private String name;
    private BigDecimal total;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
