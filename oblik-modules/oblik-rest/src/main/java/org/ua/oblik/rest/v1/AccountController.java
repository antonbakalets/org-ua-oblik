package org.ua.oblik.rest.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;

import java.util.UUID;

@RestController
@RequestMapping("v1/budgets/{budgetId}/accounts")
public class AccountController {

    private AccountService accountService;

    @ApiOperation(value = "Delete account", notes = "Delete account if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Account successfully deleted."),
            @ApiResponse(code = 400, message = "Cannot delete account due to constraint violation.", response = Integer.class),
            @ApiResponse(code = 410, message = "Cannot find account by id.", response = Integer.class)})
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Integer> deleteAccount(@PathVariable UUID budgetId,
                                                 @PathVariable Integer id) throws NotFoundException, BusinessConstraintException {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
