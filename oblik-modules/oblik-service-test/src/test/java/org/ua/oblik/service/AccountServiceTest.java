package org.ua.oblik.service;

import java.util.List;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.DefinedAccount;

/**
 *
 * @author Anton Bakalets
 */
public class AccountServiceTest extends BaseServiceTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceTest.class);
    
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
    
    @Test
    public void updateAccount() {
        LOGGER.debug("Updating account.");
        final AccountVO usdCard = accountServiceTestHelper.getDefinedAccount(DefinedAccount.USD_CARD);
        final String newName = usdCard.getName() + " (оновлена)";
        usdCard.setName(newName);
        accountService.save(usdCard);
        Assert.assertEquals(usdCard.getName(), newName);
    }
    
    @Test
    public void isNameExists() {
        LOGGER.debug("Checking is account name exists.");
        final AccountVO usdCard = accountServiceTestHelper.getDefinedAccount(DefinedAccount.USD_CARD);
        final String name = usdCard.getName();
        Assert.assertTrue(accountService.isNameExists(name));
        Assert.assertFalse(accountService.isNameExists(UUID.randomUUID().toString()));
    }
    
    @Test
    public void deleteAccount() {
        LOGGER.debug("Deleting account.");
        final AccountVO toBeDeleted = accountServiceTestHelper.getDefinedAccount(DefinedAccount.TO_BE_DELETED);
        final Integer accountId = toBeDeleted.getAccountId();
        accountService.delete(accountId);
        try {
            accountService.getAccount(accountId);
            Assert.fail("Account should be deleted.");
        } catch (EntityNotFoundException enfe) {
        }
    }
}
