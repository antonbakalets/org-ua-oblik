package org.ua.oblik.rest.v1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ua.oblik.config.AppConfig;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.CurrencyVO;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, CurrencyControllerTest.CurrencyServiceMockConfig.class})
public class CurrencyControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private CurrencyService currencyService;

    @Before
    public void setUp() {
        CurrencyController currencyController = new CurrencyController();
        currencyController.setCurrencyService(currencyService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(currencyController)
                .alwaysExpect(status().isOk())
                .alwaysDo(print())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        reset(currencyService);
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

    @Configuration
    public static class CurrencyServiceMockConfig {

        @Bean
        public CurrencyService currencyService() {
            CurrencyService currencyService = mock(CurrencyService.class);
            when(currencyService.getCurrency(10)).thenReturn(new CurrencyVO());
            return currencyService;
        }
    }
}
