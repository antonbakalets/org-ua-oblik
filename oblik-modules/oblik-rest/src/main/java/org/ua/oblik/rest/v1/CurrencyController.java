package org.ua.oblik.rest.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    private CurrencyVO toCurrencyVO(CurrencyResource dto) {
        CurrencyVO vo = new CurrencyVO();
        vo.setSymbol(dto.getSymbol());
        vo.setRate(dto.getRate());
        return vo;
    }

    @ApiOperation(value = "Create currency", notes = "Create a new currency.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Currency created.", response = CurrencyResource.class)
    })
    @PostMapping
    public ResponseEntity<CurrencyResource> postCurrency(@PathVariable UUID budgetId,
                                                         @RequestBody CurrencyResource currency) throws NotFoundException, BusinessConstraintException {
        CurrencyVO saved = currencyService.save(toCurrencyVO(currency));
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
        CurrencyVO currencyVO = toCurrencyVO(dto);
        currencyVO.setCurrencyId(id);
        CurrencyVO saved = currencyService.save(currencyVO);
        return ResponseEntity.ok(currencyResourceAssembler.toResource(saved));
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
}
