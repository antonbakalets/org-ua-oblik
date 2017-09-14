package org.ua.oblik.service;

import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.DefinedAccount;

/**
 *
 * @author Anton Bakalets
 */
public class AccountServiceTest extends BaseServiceCheckConfig {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceTest.class);
    
    @Autowired
    private AccountServiceTestHelper accountServiceTestHelper;
        
    @Autowired
    private AccountService accountService;
    
    @Test
    public void listAssetsAccounts() {
        LOGGER.debug("[TEST] Listing all assets accounts:");
        final List<AccountVO> assetsAccounts = accountService.getAccounts(
                new AccountCriteria.Builder().setType(AccountVOType.ASSETS).build());
        for (AccountVO avo : assetsAccounts) {
            LOGGER.debug("[TEST] " + avo.toString());
            Assert.assertEquals(avo.getType(), AccountVOType.ASSETS);
        }
    }
    
    @Test
    public void listIncomeAccounts() {
        LOGGER.debug("[TEST] Listing all income accounts:");
        final List<AccountVO> incomeAccounts = accountService.getAccounts(
                new AccountCriteria.Builder().setType(AccountVOType.INCOME).build());
        for (AccountVO avo : incomeAccounts) {
            LOGGER.debug("[TEST] " + avo.toString());
            Assert.assertEquals(avo.getType(), AccountVOType.INCOME);
        }
    }
    
    @Test
    public void listExpenceAccounts() {
        LOGGER.debug("[TEST] Listing all expence accounts:");
        final List<AccountVO> expenceAccounts = accountService.getAccounts(
                new AccountCriteria.Builder().setType(AccountVOType.EXPENSE).build());
        for (AccountVO avo : expenceAccounts) {
            LOGGER.debug("[TEST] " + avo.toString());
            Assert.assertEquals(avo.getType(), AccountVOType.EXPENSE);
        }
    }
    
    @Test
    public void updateAccount() {
        LOGGER.debug("[TEST] Updating account.");
        final AccountVO usdCard = accountServiceTestHelper.getDefinedAccount(DefinedAccount.USD_CARD);
        final String newName = usdCard.getName() + " (оновлена)";
        usdCard.setName(newName);
        accountService.save(usdCard);
        Assert.assertEquals(usdCard.getName(), newName);
    }
    
    @Test
    public void isNameExists() {
        LOGGER.debug("[TEST] Checking is account name exists.");
        final AccountVO usdCard = accountServiceTestHelper.getDefinedAccount(DefinedAccount.UGH_CARD);
        final String name = usdCard.getName();
        Assert.assertTrue(accountService.isNameExists(name));
        Assert.assertFalse(accountService.isNameExists(UUID.randomUUID().toString()));
    }
    
    @Test
    public void deleteAccount() {
        LOGGER.debug("[TEST] Deleting account.");
        final AccountVO toBeDeleted = accountServiceTestHelper.getDefinedAccount(DefinedAccount.TO_BE_DELETED);
        final Integer accountId = toBeDeleted.getAccountId();
        accountService.delete(accountId);
        try {
            accountService.getAccount(accountId);
            Assert.fail("[TEST] Account should be deleted.");
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
