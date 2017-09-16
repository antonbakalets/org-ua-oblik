package org.ua.oblik.rest.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.rest.v1.dto.BudgetDto;
import org.ua.oblik.service.BudgetService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.BudgetVO;

@RestController
@RequestMapping("/v1/budgets")
public class BudgetController {

    private BudgetService budgetService;

    @GetMapping
    public Map<UUID, String> budgets() {
        HashMap<UUID, String> map = new HashMap<>();
        map.put(UUID.randomUUID(), "Budget name");
        return map;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDto> budget(@PathVariable UUID id) throws NotFoundException {
            BudgetVO budgetVO = budgetService.getBudget();

            BudgetDto budgetDto = convertToDto(budgetVO);
            return ResponseEntity.ok().body(budgetDto);
    }

    private BudgetDto convertToDto(BudgetVO budgetVO) {
        UUID id = budgetVO.getBudgetId();

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setName(budgetVO.getName());
        budgetDto.setTotal(budgetVO.getTotal());

        budgetDto.add(linkTo(BudgetController.class).slash(id).withSelfRel());
        budgetDto.add(linkTo(CurrencyController.class, id).withRel("currencies"));
        budgetDto.add(linkTo(AccountController.class, id).withRel("assets"));
        budgetDto.add(linkTo(AccountController.class, id).withRel("expenses"));
        budgetDto.add(linkTo(AccountController.class, id).withRel("incomes"));
        budgetDto.add(linkTo(TransactionController.class, id).withRel("transactions"));

        return budgetDto;
    }

    @Autowired
    public void setBudgetService(BudgetService budgetService) {
        this.budgetService = budgetService;
    }
}
