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
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;

import java.util.UUID;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private MockMvc mockMvc;
    private String v1BaseUrl = "/v1/budgets/" + UUID.randomUUID().toString();

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new ExceptionHandlingControllerAdvice())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testDeleteAccountNoContent() throws Exception {
        mockMvc.perform(delete(v1BaseUrl + "/accounts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(accountService, times(1)).delete(eq(1));
    }

    @Test
    public void testDeleteAccountBadRequest() throws Exception {
        doThrow(new BusinessConstraintException("Invocation from test.")).when(accountService).delete(any());

        mockMvc.perform(delete(v1BaseUrl + "/accounts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteAccountGone() throws Exception {
        doThrow(new NotFoundException("Invocation from test.")).when(accountService).delete(any());

        mockMvc.perform(delete(v1BaseUrl + "/accounts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
