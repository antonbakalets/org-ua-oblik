package org.ua.oblik.rest.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.TransactionService;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testTransactionsGetNow() throws Exception {
        doReturn(Arrays.asList(
                createTransaction(1), createTransaction(1)
        )).when(transactionService).getTransactions(any(Date.class));

        mockMvc.perform(get(v1BaseUrl + "/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//  TODO   @Test
    public void testTransactionsGet() throws Exception {
        doReturn(Arrays.asList(
                createTransaction(1), createTransaction(1)
        )).when(transactionService).getTransactions(any(Date.class));

        mockMvc.perform(get(v1BaseUrl + "/transactions")
                .param("date", "1505677811096")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private TransactionVO createTransaction(Integer id) {
        TransactionVO vo = new TransactionVO();
        vo.setTxId(id);
        vo.setType(TransactionType.EXPENSE);
        vo.setDate(new Date());
        return vo;
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Test
    public void testTransactionPost() throws Exception {
        doAnswer(invocation -> {
            invocation.getArgumentAt(0, TransactionVO.class).setTxId(345);
            return null;
        }).when(transactionService).save(any(TransactionVO.class));

        mockMvc.perform(post(v1BaseUrl + "/transactions")
                .content(convertObjectToJsonBytes(createTransaction(null)))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(transactionService, times(1)).save(any(TransactionVO.class));
    }

    @Test
    public void testTransactionPostInvalid() throws Exception {
        doThrow(BusinessConstraintException.class).when(transactionService).save(any(TransactionVO.class));

        mockMvc.perform(post(v1BaseUrl + "/transactions")
                .content("{\"type\":\"EXPENSE\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(transactionService, times(1)).save(any(TransactionVO.class));
    }

    @Test
    public void testTransactionPatch() throws Exception {
        mockMvc.perform(patch(v1BaseUrl + "/transactions/{id}", 1)
                .content("{\"type\":\"EXPENSE\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).save(any(TransactionVO.class));
    }

    @Test
    public void testTransactionPatchInvalid() throws Exception {
        doThrow(BusinessConstraintException.class).when(transactionService).save(any(TransactionVO.class));

        mockMvc.perform(patch(v1BaseUrl + "/transactions/{id}", 1)
                .content("{\"type\":\"EXPENSE\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(transactionService, times(1)).save(any(TransactionVO.class));
    }

    @Test
    public void testTransactionPatchNotFound() throws Exception {
        doThrow(NotFoundException.class).when(transactionService).save(any(TransactionVO.class));

        mockMvc.perform(patch(v1BaseUrl + "/transactions/{id}", 1)
                .content("{\"type\":\"EXPENSE\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).save(any(TransactionVO.class));
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
