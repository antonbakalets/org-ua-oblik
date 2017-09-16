package org.ua.oblik.rest.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.TransactionService;

import java.util.UUID;

@RestController
@RequestMapping("/v1/budgets/{budgetId}/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @ApiOperation(value = "Delete transaction", notes = "Delete transaction if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Transaction successfully deleted."),
            @ApiResponse(code = 404, message = "Cannot find transaction by id.")})
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Integer> deleteTxaction(@PathVariable UUID budgetId,
                                                  @PathVariable Integer id) throws NotFoundException {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
