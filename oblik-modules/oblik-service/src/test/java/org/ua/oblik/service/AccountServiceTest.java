package org.ua.oblik.service;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.*;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.DefinedAccount;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Anton Bakalets
 */
public class AccountServiceTest extends BaseServiceCheckConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(AccountServiceTest.class);

    @Autowired
    private AccountServiceTestHelper accountServiceTestHelper;

    @Autowired
    private AccountService accountService;

    @Autowired
    protected TransactionService transactionService;

    @Test
    public void listAssetsAccounts() {
        LOGGER.debug("[TEST] Listing all assets accounts:");
        List<AccountVO> assetsAccounts = accountService.getAccounts(
                new AccountCriteria.Builder().setType(AccountVOType.ASSETS).build());
        for (AccountVO avo : assetsAccounts) {
            LOGGER.debug("[TEST] " + avo.toString());
            Assert.assertEquals(AccountVOType.ASSETS, avo.getType());
        }
    }

    @Test
    public void listIncomeAccounts() {
        LOGGER.debug("[TEST] Listing all income accounts:");
        List<AccountVO> incomeAccounts = accountService.getAccounts(
                new AccountCriteria.Builder().setType(AccountVOType.INCOME).build());
        for (AccountVO avo : incomeAccounts) {
            LOGGER.debug("[TEST] " + avo.toString());
            Assert.assertEquals(AccountVOType.INCOME, avo.getType());
        }
    }

    @Test
    public void listExpenceAccounts() {
        LOGGER.debug("[TEST] Listing all expence accounts:");
        List<AccountVO> expenceAccounts = accountService.getAccounts(
                new AccountCriteria.Builder().setType(AccountVOType.EXPENSE).build());
        for (AccountVO avo : expenceAccounts) {
            LOGGER.debug("[TEST] " + avo.toString());
            Assert.assertEquals(AccountVOType.EXPENSE, avo.getType());
        }
    }

    @Test
    public void updateAccount() {
        LOGGER.debug("[TEST] Updating account.");
        AccountVO usdCard = accountServiceTestHelper.getDefinedAccount(DefinedAccount.USD_CARD);
        String newName = usdCard.getName() + " (оновлена)";
        usdCard.setName(newName);
        try {
            accountService.save(usdCard);
        } catch (Exception e) {
            LOGGER.debug("Ignoring.", e);
        }
        Assert.assertEquals(usdCard.getName(), newName);
    }

    @Test
    public void isNameExists() {
        LOGGER.debug("[TEST] Checking if account name exists.");
        AccountVO usdCard = accountServiceTestHelper.getDefinedAccount(DefinedAccount.UGH_CARD);
        String name = usdCard.getName();
        Assert.assertTrue(accountService.isNameExists(name));
        Assert.assertFalse(accountService.isNameExists(UUID.randomUUID().toString()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteAccount() throws NotFoundException, BusinessConstraintException {
        LOGGER.debug("[TEST] Deleting account.");
        AccountVO toBeDeleted = accountServiceTestHelper.getDefinedAccount(DefinedAccount.TO_BE_DELETED);
        Integer accountId = toBeDeleted.getAccountId();
        accountService.delete(accountId);
        accountService.getAccount(accountId);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getUnknown() {
        LOGGER.debug("[TEST] Getting unknown account.");
        accountService.getAccount(-15);
    }

    @Test(expected = NotFoundException.class)
    public void deleteUnknown() throws NotFoundException, BusinessConstraintException {
        LOGGER.debug("[TEST] Deleting unknown account.");
        accountService.delete(-25);
    }

    @Test(expected = BusinessConstraintException.class)
    public void deleteUsed() throws NotFoundException, BusinessConstraintException {
        LOGGER.debug("[TEST] Deleting used account.");

        TransactionVO transferTx = new TransactionVO();
        transferTx.setDate(new Date());
        transferTx.setFirstAccount(accountServiceTestHelper.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal diff = BigDecimal.valueOf(200);
        transferTx.setFirstAmount(diff);
        transferTx.setSecondAccount(accountServiceTestHelper.accountId(DefinedAccount.UGH_CARD));
        transferTx.setNote("Переказ 200 гривень з готівки на карточку.");
        transferTx.setType(TransactionType.TRANSFER);
        transactionService.save(transferTx);

        accountService.delete(accountServiceTestHelper.accountId(DefinedAccount.UGH_CARD));
    }

    @Test
    public void totalAssets() throws NotFoundException, BusinessConstraintException {
        LOGGER.debug("[TEST] Asset account total.");

        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(accountServiceTestHelper.accountId(DefinedAccount.INCOME_SALARY));
        final BigDecimal diff = BigDecimal.valueOf(125);
        incomeTx.setFirstAmount(diff);
        incomeTx.setSecondAccount(accountServiceTestHelper.accountId(DefinedAccount.UGH_CASH));
        incomeTx.setNote("Зарплата 125 грн. зарахована до готівки.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);

        Assert.assertEquals(accountService.totalAssets().longValue(), new BigDecimal(125).longValue());
    }
}
