package org.ua.oblik.rest.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.rest.v1.convert.TransactionConverter;
import org.ua.oblik.rest.v1.convert.TransactionResourceAssembler;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.TransactionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/budgets/{budgetId}/transactions")
public class TransactionController {

    private TransactionService transactionService;

    private TransactionResourceAssembler transactionResourceAssembler;

    private TransactionConverter transactionConverter;

    @ApiOperation(value = "Delete transaction", notes = "Delete transaction if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Transaction successfully deleted."),
            @ApiResponse(code = 404, message = "Cannot find transaction by id.")})
    @DeleteMapping("/{id}")

    public ResponseEntity<Integer> deleteTxaction(@PathVariable UUID budgetId,
                                                  @PathVariable Integer id) throws NotFoundException {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setTransactionResourceAssembler(TransactionResourceAssembler transactionResourceAssembler) {
        this.transactionResourceAssembler = transactionResourceAssembler;
    }

    @Autowired
    public void setTransactionConverter(TransactionConverter transactionConverter) {
        this.transactionConverter = transactionConverter;
    }
}
