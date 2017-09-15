package org.ua.oblik.rest.v1;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ua.oblik.service.BudgetService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.BudgetVO;

@RunWith(MockitoJUnitRunner.class)
public class BudgetControllerTest {

    protected MockMvc mockMvc;

    @InjectMocks
    private BudgetController budgetController;

    @Mock
    private BudgetService budgetService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(budgetController)
                .alwaysDo(print())
                .build();
    }

//    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/v1/budgets")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.name", is("Budget Name")));
    }

    @Test
    public void testGet() throws Exception {
        BudgetVO t = new BudgetVO();
        t.setTotal(BigDecimal.TEN);
        t.setName("Budget Name");
        when(budgetService.getBudget()).thenReturn(t);

        mockMvc.perform(get("/v1/budgets/{id}", UUID.randomUUID().toString())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total", is(10)))
                .andExpect(jsonPath("$.name", is("Budget Name")))
                .andExpect(jsonPath("$.links", org.hamcrest.Matchers.hasSize(6)));
    }

    @Test
    public void testNotFound() throws Exception {
        when(budgetService.getBudget()).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/v1/budgets/{id}", UUID.randomUUID().toString())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(isEmptyOrNullString()));
    }
}
