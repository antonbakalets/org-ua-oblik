package org.ua.oblik.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

    private AccountService accountService;

    @ApiOperation(value = "Delete account", notes = "Delete account if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Account successfully deleted."),
            @ApiResponse(code = 400, message = "Cannot delete account due to constraint violation.", response = Integer.class),
            @ApiResponse(code = 410, message = "Cannot find account by id.", response = Integer.class)})
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Integer> deleteAccount(@PathVariable Integer id) {
        ResponseEntity<Integer> responseEntity;
        try {
            accountService.delete(id);
            responseEntity = ResponseEntity.noContent().build(); // 204
        } catch (BusinessConstraintException e) {
            responseEntity = ResponseEntity.badRequest().body(id); // 400
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.GONE).body(id); // 410
        }
        return responseEntity;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
