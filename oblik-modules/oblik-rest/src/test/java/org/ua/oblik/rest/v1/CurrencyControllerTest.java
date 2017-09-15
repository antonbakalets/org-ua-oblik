package org.ua.oblik.rest.v1;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.CurrencyVO;

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
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testCurrencyPost() throws Exception {
        mockMvc.perform(post(v1BaseUrl + "/currencies")
                .content("{}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(currencyService, times(1)).save(any(CurrencyVO.class));
    }

    @Test
    public void testCurrencyPut() throws Exception {
        mockMvc.perform(patch(v1BaseUrl + "/currencies/{id}", 1)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(currencyService, times(1)).getCurrency(eq(1));
        verify(currencyService, times(1)).save(any(CurrencyVO.class));
    }

    @Test
    public void testDeleteCurrencyNoContent() throws Exception {
        mockMvc.perform(delete(v1BaseUrl + "/currencies/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(currencyService, times(1)).remove(eq(1));
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
                .andExpect(status().isGone());
    }
}
