package org.ua.oblik.rest.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.rest.v1.convert.TransactionConverter;
import org.ua.oblik.rest.v1.convert.TransactionResourceAssembler;
import org.ua.oblik.rest.v1.dto.TransactionResource;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.TransactionService;
import org.ua.oblik.service.beans.TransactionVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/budgets/{budgetId}/transactions")
public class TransactionController {

    private TransactionService transactionService;

    private TransactionResourceAssembler transactionResourceAssembler;

    private TransactionConverter transactionConverter;

    @ApiOperation(value = "List transactions", notes = "List all transactions.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currencies.", response = List.class)
    })
    @GetMapping
    public ResponseEntity<List<TransactionResource>> getTransactions(@PathVariable UUID budgetId,
                @RequestParam(required = false) @DateTimeFormat(pattern = "MMyyyy") Date date) {
        Date month = date == null ? new Date() : date;
        List<TransactionResource> list = transactionService.getTransactions(month).stream()
                .map(transactionResourceAssembler::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Create transaction", notes = "Create a new transaction.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Transaction created.", response = TransactionResource.class)
    })
    @PostMapping
    public ResponseEntity<TransactionResource> postTransaction(@PathVariable UUID budgetId,
                @RequestBody TransactionResource transaction) throws NotFoundException, BusinessConstraintException {
        TransactionVO saved = transactionConverter.convert(transaction);
        transactionService.save(saved);
        ControllerLinkBuilder uriBuilder = linkTo(TransactionController.class, budgetId).slash(saved.getTxId());
        return ResponseEntity.created(uriBuilder.toUri())
                .body(transactionResourceAssembler.toResource(saved));
    }

    @ApiOperation(value = "Update transaction", notes = "Update an existing transaction.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transaction updated.", response = TransactionResource.class)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TransactionResource> patchTransaction(@PathVariable UUID budgetId, @PathVariable Integer id,
                                                          @RequestBody TransactionResource dto) throws NotFoundException, BusinessConstraintException {
        TransactionVO transactionVO = transactionConverter.convert(dto);
        transactionVO.setTxId(id);
        transactionService.save(transactionVO);
        return ResponseEntity.ok(transactionResourceAssembler.toResource(transactionVO));
    }


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
