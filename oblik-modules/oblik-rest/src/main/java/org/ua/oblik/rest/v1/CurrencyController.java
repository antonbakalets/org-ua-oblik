package org.ua.oblik.rest.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ua.oblik.rest.v1.dto.CurrencyDto;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.BudgetChange;
import org.ua.oblik.service.beans.CurrencyVO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/v1/budgets/{budgetId}/currencies")
public class CurrencyController {

    private CurrencyService currencyService;

    @ApiOperation(value = "List currencies", notes = "List all currencies.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currencies.", response = List.class)
    })
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<CurrencyDto>> getCurrencies(@PathVariable UUID budgetId) {
        List<CurrencyDto> list = currencyService.getCurrencies().stream().map(vo -> {
            CurrencyDto dto = new CurrencyDto();
            dto.setCurrencyId(vo.getCurrencyId());
            dto.setDefaultRate(vo.getDefaultRate());
            dto.setSymbol(vo.getSymbol());
            dto.setRate(vo.getRate());
            dto.setTotal(vo.getTotal());
            dto.add(linkTo(CurrencyController.class, budgetId).slash(vo.getCurrencyId()).withSelfRel());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    @ResponseBody
    public BudgetChange postCurrency(@PathVariable UUID budgetId, @RequestBody CurrencyVO currency) {
        currencyService.save(currency);
        return new BudgetChange();
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public BudgetChange patchCurrency(@PathVariable UUID budgetId, @PathVariable Integer id) {
        CurrencyVO currency = currencyService.getCurrency(id);
        currencyService.save(currency);
        return new BudgetChange();
    }

    @ApiOperation(value = "Delete currency", notes = "Delete currency if exists and is not used.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Currency successfully deleted."),
            @ApiResponse(code = 400, message = "Cannot delete currency due to constraint violation.", response = Integer.class),
            @ApiResponse(code = 410, message = "Cannot find currency by id.", response = Integer.class)})
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Integer> deleteCurrency(@PathVariable UUID budgetId,
                                                  @PathVariable Integer id) throws NotFoundException, BusinessConstraintException {
        currencyService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
}
