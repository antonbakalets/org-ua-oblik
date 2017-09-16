package org.ua.oblik.rest.v1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ua.oblik.rest.v1.convert.CurrencyResourceAssembler;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.CurrencyVO;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

    private MockMvc mockMvc;
    private String v1BaseUrl = "/v1/budgets/" + UUID.randomUUID().toString();

    @InjectMocks
    private CurrencyController currencyController;

    @Mock
    private CurrencyService currencyService;


    @Before
    public void setUp() {
        currencyController.setCurrencyResourceAssembler(new CurrencyResourceAssembler());

        mockMvc = MockMvcBuilders.standaloneSetup(currencyController)
                .setControllerAdvice(new ExceptionHandlingControllerAdvice())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testCurrencyGet() throws Exception {
        when(currencyService.getCurrencies()).thenReturn(Arrays.asList(
                createVO(0), createVO(1)
        ));
        mockMvc.perform(get(v1BaseUrl + "/currencies")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private CurrencyVO createVO(Integer id) {
        CurrencyVO currencyVO = new CurrencyVO();
        currencyVO.setCurrencyId(id);
        currencyVO.setSymbol("S" + id);
        currencyVO.setRate(new BigDecimal(5 * id + 1));
        currencyVO.setDefaultRate(id == 0);
        currencyVO.setTotal(new BigDecimal(2000 * id));
        return currencyVO;
    }

    @Test
    public void testCurrencyPost() throws Exception {
        CurrencyVO currencyVO = new CurrencyVO();
        currencyVO.setCurrencyId(55);
        when(currencyService.save(any(CurrencyVO.class))).thenReturn(currencyVO);

        mockMvc.perform(post(v1BaseUrl + "/currencies")
                .content("{}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(currencyService, times(1)).save(any(CurrencyVO.class));
    }

    @Test
    public void testCurrencyPatch() throws Exception {
        CurrencyVO currencyVO = new CurrencyVO();
        currencyVO.setCurrencyId(55);
        when(currencyService.save(any(CurrencyVO.class))).thenReturn(currencyVO);

        mockMvc.perform(patch(v1BaseUrl + "/currencies/{id}", 1)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(currencyService, times(1)).save(any(CurrencyVO.class));
    }

    @Test
    public void testDeleteCurrencyNoContent() throws Exception {
        mockMvc.perform(delete(v1BaseUrl + "/currencies/{id}", 55)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(currencyService, times(1)).remove(eq(55));
    }

    @Test
    public void testDeleteCurrencyBadRequest() throws Exception {
        doThrow(new BusinessConstraintException("Invocation from test.")).when(currencyService).remove(any());

        mockMvc.perform(delete(v1BaseUrl + "/currencies/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteCurrencyGone() throws Exception {
        doThrow(new NotFoundException("Invocation from test.")).when(currencyService).remove(any());

        mockMvc.perform(delete(v1BaseUrl + "/currencies/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
