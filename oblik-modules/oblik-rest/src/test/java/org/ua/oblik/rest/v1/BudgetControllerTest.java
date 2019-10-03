package org.ua.oblik.rest.v1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ua.oblik.rest.v1.convert.BudgetResourceAssembler;
import org.ua.oblik.service.BudgetService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.BudgetVO;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class BudgetControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BudgetController budgetController;

    @Mock
    private BudgetService budgetService;

    @Before
    public void setUp() {
        budgetController.setBudgetResourceAssembler(new BudgetResourceAssembler());

        mockMvc = MockMvcBuilders.standaloneSetup(budgetController)
                .setControllerAdvice(new ExceptionHandlingControllerAdvice())
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
    public void testBudgetGet() throws Exception {
        BudgetVO t = new BudgetVO();
        t.setBudgetId(UUID.randomUUID());
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
    public void testBudgetNotFound() throws Exception {
        when(budgetService.getBudget()).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/v1/budgets/{id}", UUID.randomUUID().toString())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(isEmptyOrNullString()));
    }
}
