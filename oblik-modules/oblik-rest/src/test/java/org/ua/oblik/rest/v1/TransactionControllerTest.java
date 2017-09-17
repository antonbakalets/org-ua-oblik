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
import org.ua.oblik.rest.v1.convert.TransactionConverter;
import org.ua.oblik.rest.v1.convert.TransactionResourceAssembler;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.TransactionService;

import java.util.UUID;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;
    private String v1BaseUrl = "/v1/budgets/" + UUID.randomUUID().toString();

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Before
    public void setUp() {
        transactionController.setTransactionResourceAssembler(new TransactionResourceAssembler());
        transactionController.setTransactionConverter(new TransactionConverter());

        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new ExceptionHandlingControllerAdvice())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testTransactionsGet() throws Exception {
//        when(transactionService.getTransactions())
    }

    @Test
    public void testTransactionDeleteNoContent() throws Exception {
        mockMvc.perform(delete(v1BaseUrl + "/transactions/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).delete(eq(1));
    }

    @Test
    public void testTransactionDeleteNotFound() throws Exception {
        doThrow(new NotFoundException("Invocation from test.")).when(transactionService).delete(any());

        mockMvc.perform(delete(v1BaseUrl + "/transactions/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
