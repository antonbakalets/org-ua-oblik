package org.ua.oblik.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.TransactionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @ApiOperation(value = "Delete transaction", notes = "Delete transaction if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Transaction successfully deleted."),
            @ApiResponse(code = 410, message = "Cannot find transaction by id.", response = Integer.class)})
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Integer> deleteTxaction(@PathVariable Integer id) {
        ResponseEntity<Integer> responseEntity;
        try {
            transactionService.delete(id);
            responseEntity = ResponseEntity.noContent().build(); // 204
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.GONE).body(id); // 410
        }
        return responseEntity;
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
