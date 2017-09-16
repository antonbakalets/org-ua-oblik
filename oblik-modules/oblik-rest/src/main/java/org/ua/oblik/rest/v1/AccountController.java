package org.ua.oblik.rest.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ua.oblik.rest.v1.convert.AccountConverter;
import org.ua.oblik.rest.v1.convert.AccountResourceAssembler;
import org.ua.oblik.rest.v1.dto.AccountResource;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.AccountCriteria;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("v1/budgets/{budgetId}")
public class AccountController {

    private AccountService accountService;

    private AccountResourceAssembler accountResourceAssembler;

    private AccountConverter accountConverter;

    @ApiOperation(value = "List assets.", notes = "List all assets.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Assets.", response = List.class)
    })
    @GetMapping("/assets")
    public ResponseEntity<List<AccountResource>> getAssets(@PathVariable UUID budgetId) {
        return ResponseEntity.ok(getAccounts(AccountCriteria.ASSETS_CRITERIA));
    }

    @ApiOperation(value = "List expenses.", notes = "List all expenses.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Expenses.", response = List.class)
    })
    @GetMapping("/expenses")
    public ResponseEntity<List<AccountResource>> getExpenses(@PathVariable UUID budgetId) {
        return ResponseEntity.ok(getAccounts(AccountCriteria.EXPENSE_CRITERIA));
    }

    @ApiOperation(value = "List incomes.", notes = "List all incomes.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Incomes.", response = List.class)
    })
    @GetMapping("/incomes")
    public ResponseEntity<List<AccountResource>> getIncomes(@PathVariable UUID budgetId) {
        return ResponseEntity.ok(getAccounts(AccountCriteria.INCOME_CRITERIA));
    }

    private List<AccountResource> getAccounts(AccountCriteria accountCriteria) {
        return accountService.getAccounts(accountCriteria).stream()
                .map(accountResourceAssembler::toResource)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Delete account", notes = "Delete account if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Account successfully deleted."),
            @ApiResponse(code = 400, message = "Cannot delete account due to constraint violation.", response = String.class),
            @ApiResponse(code = 404, message = "Cannot find account by id.")})
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Integer> deleteAccount(@PathVariable UUID budgetId,
                                                 @PathVariable Integer id) throws NotFoundException, BusinessConstraintException {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setAccountResourceAssembler(AccountResourceAssembler accountResourceAssembler) {
        this.accountResourceAssembler = accountResourceAssembler;
    }

    @Autowired
    public void setAccountConverter(AccountConverter accountConverter) {
        this.accountConverter = accountConverter;
    }
}
