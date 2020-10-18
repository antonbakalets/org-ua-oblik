package org.ua.oblik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ua.oblik.service.beans.BudgetVO;

import java.util.UUID;

@Service
public class BudgetServiceImpl implements BudgetService {

    private TotalService totalService;

    @Override
    public BudgetVO getBudget() {
        BudgetVO budgetVO = new BudgetVO();
        budgetVO.setBudgetId(UUID.randomUUID());
        budgetVO.setName("Hello word.");
        budgetVO.setTotal(totalService.getDefaultCurrencyTotal());
        return budgetVO;
    }

    @Autowired
    public void setTotalService(TotalService totalService) {
        this.totalService = totalService;
    }
}
