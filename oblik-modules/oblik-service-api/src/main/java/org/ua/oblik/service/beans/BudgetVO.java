package org.ua.oblik.service.beans;

import java.math.BigDecimal;
import java.util.UUID;

public class BudgetVO {

    private UUID budgetId;
    private BigDecimal total;
    private String name;

    public UUID getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(UUID budgetId) {
        this.budgetId = budgetId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
