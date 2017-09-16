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
import org.ua.oblik.rest.v1.convert.AccountConverter;
import org.ua.oblik.rest.v1.convert.AccountResourceAssembler;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        accountController.setAccountResourceAssembler(new AccountResourceAssembler());
        accountController.setAccountConverter(new AccountConverter());

        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new ExceptionHandlingControllerAdvice())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testAssetsGet() throws Exception {
        when(accountService.getAccounts(eq(AccountCriteria.ASSETS_CRITERIA))).thenReturn(Arrays.asList(
                createVO(0, AccountVOType.ASSETS), createVO(1, AccountVOType.ASSETS)
        ));
        mockMvc.perform(get(v1BaseUrl + "/assets")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testExpenseGet() throws Exception {
        when(accountService.getAccounts(eq(AccountCriteria.EXPENSE_CRITERIA))).thenReturn(Arrays.asList(
                createVO(2, AccountVOType.EXPENSE), createVO(3, AccountVOType.EXPENSE)
        ));
        mockMvc.perform(get(v1BaseUrl + "/expenses")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testIncomesGet() throws Exception {
        when(accountService.getAccounts(eq(AccountCriteria.INCOME_CRITERIA))).thenReturn(Arrays.asList(
                createVO(4, AccountVOType.INCOME), createVO(5, AccountVOType.INCOME)
        ));
        mockMvc.perform(get(v1BaseUrl + "/incomes")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private AccountVO createVO(Integer id, AccountVOType type) {
        AccountVO vo = new AccountVO();
        vo.setAccountId(id);
        vo.setType(type);
        vo.setName("account " + id);
        vo.setCurrencySymbol("S" + id);
        vo.setAmount(BigDecimal.TEN);
        vo.setCurrencyId(1);
        return vo;
    }

    @Test
    public void testAccountPost() throws Exception {
        doAnswer(invocation -> {
            invocation.getArgumentAt(0, AccountVO.class).setAccountId(458);
            return null;
        }).when(accountService).save(any(AccountVO.class));

        mockMvc.perform(post(v1BaseUrl + "/accounts")
                .content("{\"type\":\"EXPENSE\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(accountService, times(1)).save(any(AccountVO.class));
    }

    @Test
    public void testAccountPostInvalid() throws Exception {
        doThrow(BusinessConstraintException.class).when(accountService).save(any(AccountVO.class));

        mockMvc.perform(post(v1BaseUrl + "/accounts")
                .content("{\"type\":\"EXPENSE\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(accountService, times(1)).save(any(AccountVO.class));
    }



    @Test
    public void testAccountPatch() throws Exception {
        mockMvc.perform(patch(v1BaseUrl + "/accounts/{id}", 3)
                .content("{\"type\":\"EXPENSE\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountService, times(1)).save(any(AccountVO.class));
    }

    @Test
    public void testAccountDeleteNoContent() throws Exception {
        mockMvc.perform(delete(v1BaseUrl + "/accounts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(accountService, times(1)).delete(eq(1));
    }

    @Test
    public void testAccountDeleteBadRequest() throws Exception {
        doThrow(new BusinessConstraintException("Invocation from test.")).when(accountService).delete(any());

        mockMvc.perform(delete(v1BaseUrl + "/accounts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testAccountDeleteNotFound() throws Exception {
        doThrow(new NotFoundException("Invocation from test.")).when(accountService).delete(any());

        mockMvc.perform(delete(v1BaseUrl + "/accounts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
