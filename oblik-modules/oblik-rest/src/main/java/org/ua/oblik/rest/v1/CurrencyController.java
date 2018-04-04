package org.ua.oblik.rest.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ua.oblik.rest.v1.convert.CurrencyConverter;
import org.ua.oblik.rest.v1.convert.CurrencyResourceAssembler;
import org.ua.oblik.rest.v1.dto.CurrencyResource;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.CurrencyVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/budgets/{budgetId}/currencies")
public class CurrencyController {

    private CurrencyService currencyService;

    private CurrencyResourceAssembler currencyResourceAssembler;

    private CurrencyConverter currencyConverter;

    @ApiOperation(value = "List currencies", notes = "List all currencies.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currencies.", response = List.class)
    })
    @GetMapping
    public ResponseEntity<List<CurrencyResource>> getCurrencies(@PathVariable UUID budgetId) {
        List<CurrencyResource> list = currencyService.getCurrencies().stream()
                .map(currencyResourceAssembler::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Create currency", notes = "Create a new currency.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Currency created.", response = CurrencyResource.class)
    })
    @PostMapping
    public ResponseEntity<CurrencyResource> postCurrency(@PathVariable UUID budgetId,
                                                         @RequestBody CurrencyResource currency) throws NotFoundException, BusinessConstraintException {
        CurrencyVO saved = currencyConverter.convert(currency);
        currencyService.save(saved);
        ControllerLinkBuilder uriBuilder = linkTo(CurrencyController.class, budgetId).slash(saved.getCurrencyId());
        return ResponseEntity.created(uriBuilder.toUri())
                .body(currencyResourceAssembler.toResource(saved));
    }

    @ApiOperation(value = "Update currency", notes = "Update an existing currency.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currency updated.", response = CurrencyResource.class)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CurrencyResource> patchCurrency(@PathVariable UUID budgetId, @PathVariable Integer id,
                                                          @RequestBody CurrencyResource dto) throws NotFoundException, BusinessConstraintException {
        CurrencyVO currencyVO = currencyConverter.convert(dto);
        currencyVO.setCurrencyId(id);
        currencyService.save(currencyVO);
        return ResponseEntity.ok(currencyResourceAssembler.toResource(currencyVO));
    }

    @ApiOperation(value = "Delete currency", notes = "Delete currency if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Currency successfully deleted."),
            @ApiResponse(code = 400, message = "Cannot delete currency due to constraint violation.", response = String.class),
            @ApiResponse(code = 404, message = "Cannot find currency by id.")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteCurrency(@PathVariable UUID budgetId,
                @PathVariable Integer id) throws NotFoundException, BusinessConstraintException {
        currencyService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Autowired
    public void setCurrencyResourceAssembler(CurrencyResourceAssembler currencyResourceAssembler) {
        this.currencyResourceAssembler = currencyResourceAssembler;
    }

    @Autowired
    public void setCurrencyConverter(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }
}
