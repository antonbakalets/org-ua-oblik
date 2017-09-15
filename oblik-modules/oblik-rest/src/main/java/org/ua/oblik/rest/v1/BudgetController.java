package org.ua.oblik.rest.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ua.oblik.service.BudgetService;
import org.ua.oblik.service.beans.BudgetVO;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    public static final UUID KEY = UUID.randomUUID();

    private BudgetService budgetService;

    @GetMapping()
    @ResponseBody
    public Map<UUID, String> budgets() {
        HashMap<UUID, String> map = new HashMap<>();
        map.put(KEY, "Budget name");
        return map;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BudgetVO budget(@PathVariable UUID id) {
        return budgetService.getBudget();
    }

    @Autowired
    public void setBudgetService(BudgetService budgetService) {
        this.budgetService = budgetService;
    }
}
