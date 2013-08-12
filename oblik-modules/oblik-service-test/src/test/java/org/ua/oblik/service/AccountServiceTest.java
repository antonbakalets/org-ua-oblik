package org.ua.oblik.service;

import java.util.List;
import junit.framework.Assert;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.test.AccountServiceTestHelper;

/**
 *
 * @author Anton Bakalets
 */
public class AccountServiceTest extends BaseServiceTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceTest.class);
    
    @Autowired
    private CurrencyServiceTestHelper currencyServiceTestHelper;
    
    @Autowired
    private AccountServiceTestHelper accountServiceTestHelper;
        
    @Autowired
    private AccountService accountService;
    
    @Test
    public void listAssetsAccounts() {
        LOGGER.debug("Listing all assets accounts:");
        final List<AccountVO> assetsAccounts = accountService.getAssetsAccounts();
        for (AccountVO avo : assetsAccounts) {
            LOGGER.debug(avo.toString());
            Assert.assertEquals(avo.getType(), AccountVOType.ASSETS);
        }
    }
    
    @Test
    public void listIncomeAccounts() {
        LOGGER.debug("Listing all income accounts:");
        final List<AccountVO> incomeAccounts = accountService.getIncomeAccounts();
        for (AccountVO avo : incomeAccounts) {
            LOGGER.debug(avo.toString());
            Assert.assertEquals(avo.getType(), AccountVOType.INCOME);
        }
    }
    
    @Test
    public void listExpenceAccounts() {
        LOGGER.debug("Listing all expence accounts:");
        final List<AccountVO> expenceAccounts = accountService.getExpenseAccounts();
        for (AccountVO avo : expenceAccounts) {
            LOGGER.debug(avo.toString());
            Assert.assertEquals(avo.getType(), AccountVOType.EXPENSE);
        }
    }
}
