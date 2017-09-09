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
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.CurrencyVO;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CurrencyController currencyController;

    @Mock
    private CurrencyService currencyService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(currencyController)
                .alwaysExpect(status().isOk())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testCurrencyPost() throws Exception {
        mockMvc.perform(post("/currency")
                .content("{}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
        verify(currencyService, times(1)).save(any(CurrencyVO.class));
    }

    @Test
    public void testCurrencyPut() throws Exception {
        mockMvc.perform(put("/currency/{id}", 1)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
        verify(currencyService, times(1)).getCurrency(eq(1));
        verify(currencyService, times(1)).save(any(CurrencyVO.class));
    }

    @Test
    public void testCurrencyDelete() throws Exception {
        mockMvc.perform(delete("/currency/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
        verify(currencyService, times(1)).remove(eq(1));
    }
}
