package org.ua.oblik.rest.v1.dto;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.UUID;

public class BudgetDto extends ResourceSupport {

    private UUID budgetId;
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

    public void setBudgetId(UUID budgetId) {
        this.budgetId = budgetId;
    }

    public UUID getBudgetId() {
        return budgetId;
    }
}
