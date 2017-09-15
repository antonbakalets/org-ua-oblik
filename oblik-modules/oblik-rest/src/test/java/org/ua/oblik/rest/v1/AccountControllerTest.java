package org.ua.oblik.rest.v1;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;

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
                .andExpect(status().isGone());
    }
}
