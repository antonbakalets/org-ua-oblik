package org.ua.oblik.rest.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    public static final UUID KEY = UUID.randomUUID();

    @GetMapping()
    @ResponseBody
    public Map<UUID, String> budgets() {
        HashMap<UUID, String> map = new HashMap<>();
        map.put(KEY, "Budget name");
        return map;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String budget(@PathVariable UUID id) {
        return "BudgetName" + id;
    }
}
