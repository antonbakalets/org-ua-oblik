package org.ua.oblik.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.BudgetChange;
import org.ua.oblik.service.beans.CurrencyVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private CurrencyService currencyService;

    @PostMapping
    @ResponseBody
    public BudgetChange postCurrency(@RequestBody CurrencyVO currency) {
        currencyService.save(currency);
        return new BudgetChange();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public BudgetChange putCurrency(@PathVariable Integer id) {
        CurrencyVO currency = currencyService.getCurrency(id);
        currencyService.save(currency);
        return new BudgetChange();
    }

    @ApiOperation(value = "deleteCurrency", notes = "Delete currency note.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Currency successfully deleted.", response = Void.class),
            @ApiResponse(code = 400, message = "Cannot delete currency due to constraint violation.", response = Integer.class),
            @ApiResponse(code = 410, message = "Cannot find currency by id.", response = Integer.class)})
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Integer> deleteCurrency(@PathVariable Integer id) {
        ResponseEntity<Integer> responseEntity;
        try {
            currencyService.remove(id);
            responseEntity = ResponseEntity.noContent().build(); // 204
        } catch (BusinessConstraintException e) {
            responseEntity = ResponseEntity.badRequest().body(id); // 400
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.GONE).body(id); // 410
        }
        return responseEntity;
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
}
