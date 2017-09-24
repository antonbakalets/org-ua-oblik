package org.ua.oblik.rest.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.rest.v1.convert.BudgetResourceAssembler;
import org.ua.oblik.rest.v1.dto.BudgetResource;
import org.ua.oblik.service.BudgetService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.BudgetVO;

@RestController
@RequestMapping("/v1/budgets")
public class BudgetController {

    private BudgetService budgetService;

    private BudgetResourceAssembler budgetResourceAssembler;

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResource> budget(@PathVariable Optional<UUID> id) throws NotFoundException {
        BudgetVO budgetVO = budgetService.getBudget();
        return ResponseEntity.ok().body(budgetResourceAssembler.toResource(budgetVO));
    }

    @Autowired
    public void setBudgetService(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Autowired
    public void setBudgetResourceAssembler(BudgetResourceAssembler budgetResourceAssembler) {
        this.budgetResourceAssembler = budgetResourceAssembler;
    }
}
