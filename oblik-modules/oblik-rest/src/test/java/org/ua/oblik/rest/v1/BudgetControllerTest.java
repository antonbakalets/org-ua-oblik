package org.ua.oblik.rest.v1;

import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ua.oblik.config.AppConfig;
import org.ua.oblik.service.BudgetService;
import org.ua.oblik.service.beans.BudgetVO;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, BudgetControllerTest.BudgetServiceMockConfig.class})
public class BudgetControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private BudgetService budgetService;

    @Before
    public void setUp() {
        BudgetController budgetController = new BudgetController();
        budgetController.setBudgetService(budgetService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(budgetController)
                .alwaysExpect(status().isOk())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/budget")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("{\"" + BudgetController.KEY.toString() + "\":\"Budget name\"}", contentAsString);
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/budget/{id}", UUID.randomUUID().toString())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.total", is(10)));
    }

    @Configuration
    public static class BudgetServiceMockConfig {

        @Bean
        public BudgetService budgetService() {
            BudgetService budgetService = mock(BudgetService.class);
            BudgetVO t = new BudgetVO();
            t.setTotal(BigDecimal.TEN);
            when(budgetService.getBudget()).thenReturn(t);
            return budgetService;
        }
    }
}
