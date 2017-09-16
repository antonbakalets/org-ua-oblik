package org.ua.oblik.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.rest.v1.dto.AccountDto;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("v1/budgets/{budgetId}")
public class AccountController {

    private AccountService accountService;

    @GetMapping("/assets")
    @ResponseBody
    public ResponseEntity<List<AccountDto>> getAssets(@PathVariable UUID budgetId) {
        List<AccountVO> assets = accountService.getAccounts(AccountCriteria.ASSETS_CRITERIA);
        List<AccountDto> accountDtos = assets.stream().map(vo -> {
            AccountDto dto = new AccountDto();


            dto.add(linkTo(AccountController.class, budgetId).slash(vo.getAccountId()).withSelfRel());

            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(accountDtos);
    }

    @ApiOperation(value = "Delete account", notes = "Delete account if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Account successfully deleted."),
            @ApiResponse(code = 400, message = "Cannot delete account due to constraint violation.", response = String.class),
            @ApiResponse(code = 404, message = "Cannot find account by id.")})
    @DeleteMapping("/accounts/{id}")
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
