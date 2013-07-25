package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;
import org.ua.oblik.service.test.AccountServiceTestHelper;
import org.ua.oblik.service.test.CurrencyServiceTestHelper;
import org.ua.oblik.service.test.DefinedAccount;
import org.ua.oblik.service.test.DefinedCurrency;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionServiceTest extends BaseServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceTest.class);

    @Autowired
    private CurrencyServiceTestHelper currencyHelper;

    @Autowired
    private AccountServiceTestHelper accountHelper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TotalService totalService;

    private Map<DefinedAccount, Integer> accountIds;

    @Before
    public void createCurrenciesAndAccounts() {
        accountIds = new EnumMap<DefinedAccount, Integer>(DefinedAccount.class);
        for (DefinedAccount da : DefinedAccount.values()) {
            accountIds.put(da, accountHelper.getDefinedAccount(da).getAccountId());
        }
    }

    @Test
    public void income() {
        LOGGER.debug("[TEST] income");
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();

        final Integer incomeSalaryAccountId = accountIds.get(DefinedAccount.INCOME_SALARY);
        final Integer incomeSalaryCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.INCOME_SALARY).getCurrencyId();
        final BigDecimal totalBefore = totalService.getCurrencyTotal(incomeSalaryCurrencyId);
        Assert.assertNotNull(totalBefore);

        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(incomeSalaryAccountId);
        final BigDecimal diff = BigDecimal.valueOf(125);
        incomeTx.setFirstAmmount(diff);
        incomeTx.setSecondAccount(accountIds.get(DefinedAccount.UGH_CASH));
        incomeTx.setNote("Зарплата 125 грн. зарахована до готівки.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);

        final BigDecimal totalAfter = totalService.getCurrencyTotal(incomeSalaryCurrencyId);
        Assert.assertEquals("", totalAfter, totalBefore.add(diff));

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals("", defaultAfter, defaultBefore.add(diff));
    }
 
    @Test
    public void negativeIncome() {
        LOGGER.debug("[TEST] negativeIncome");
        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(accountIds.get(DefinedAccount.INCOME_FOUND_USD));
        incomeTx.setFirstAmmount(BigDecimal.valueOf(-85));
        incomeTx.setSecondAccount(accountIds.get(DefinedAccount.USD_CASH));
        incomeTx.setNote("Нонсенс. Зарплата -85 грн. зарахована до готівки в доларах.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);
        Assert.fail("Test should not pass. Different accounts currencies.");
    }

    @Test
    public void expense() {
        LOGGER.debug("[TEST] expense");
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();

        final Integer expenseMarketAccountId = accountIds.get(DefinedAccount.EXPENSE_MARKET);
        final Integer expenseMarketCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.EXPENSE_MARKET).getCurrencyId();
        final BigDecimal totalBefore = totalService.getCurrencyTotal(expenseMarketCurrencyId);
        Assert.assertNotNull(totalBefore);

        TransactionVO expenseTx = new TransactionVO();
        expenseTx.setDate(new Date());
        expenseTx.setFirstAccount(expenseMarketAccountId);
        final BigDecimal diff = BigDecimal.valueOf(65);
        expenseTx.setFirstAmmount(diff);
        expenseTx.setSecondAccount(accountIds.get(DefinedAccount.UGH_CASH));
        expenseTx.setNote("Витрата 65 грн. із готівки на базарі.");
        expenseTx.setType(TransactionType.EXPENSE);
        transactionService.save(expenseTx);

        final BigDecimal totalAfter = totalService.getCurrencyTotal(expenseMarketCurrencyId);
        Assert.assertEquals("", totalAfter, totalBefore.subtract(diff));

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals("", defaultAfter, defaultBefore.subtract(diff));
    }

    @Test
    public void transfer() {
        LOGGER.debug("[TEST] transfer");
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();

        final Integer firstAccountId = accountIds.get(DefinedAccount.UGH_CASH);
        final Integer secondAccountId = accountIds.get(DefinedAccount.UGH_CARD);
        final Integer currencyId = currencyHelper.getDefinedCurrency(DefinedCurrency.UGH).getCurrencyId();
        final BigDecimal totalBefore = totalService.getCurrencyTotal(currencyId);
        Assert.assertNotNull(totalBefore);

        TransactionVO transferTx = new TransactionVO();
        transferTx.setDate(new Date());
        transferTx.setFirstAccount(firstAccountId);
        final BigDecimal diff = BigDecimal.valueOf(200);
        transferTx.setFirstAmmount(diff);
        transferTx.setSecondAccount(secondAccountId);
        transferTx.setNote("Переказ 200 гривень з готівки на карточку.");
        transferTx.setType(TransactionType.TRANSFER);
        transactionService.save(transferTx);

        final BigDecimal totalAfter = totalService.getCurrencyTotal(currencyId);
        Assert.assertEquals("", totalAfter, totalBefore);

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals("", defaultAfter, defaultBefore);
    }

    @Test
    public void exchangeToDefault() {
        LOGGER.debug("[TEST] exchangeToDefault");
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();

        final Integer firstAccountId = accountIds.get(DefinedAccount.EURO_CARD);
        final Integer firstCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.EURO_CARD).getCurrencyId();
        final BigDecimal firstTotalBefore = totalService.getCurrencyTotal(firstCurrencyId);
        Assert.assertNotNull(firstTotalBefore);

        final Integer secondAccountId = accountIds.get(DefinedAccount.UGH_DEPOSIT);
        final Integer secondCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.UGH_DEPOSIT).getCurrencyId();
        final BigDecimal secondTotalBefore = totalService.getCurrencyTotal(secondCurrencyId);
        Assert.assertNotNull(secondTotalBefore);

        TransactionVO exchangeTx = new TransactionVO();
        exchangeTx.setDate(new Date());
        exchangeTx.setFirstAccount(firstAccountId);
        final BigDecimal firstDiff = BigDecimal.valueOf(10);
        exchangeTx.setFirstAmmount(firstDiff);
        exchangeTx.setSecondAccount(secondAccountId);
        final BigDecimal secondDiff = BigDecimal.valueOf(107);
        exchangeTx.setSecondAmmount(secondDiff);
        exchangeTx.setNote("Обмін 10 євро з карточки в 107 гривень на депозиті.");
        exchangeTx.setType(TransactionType.TRANSFER);
        transactionService.save(exchangeTx);

        final BigDecimal firstTotalAfter = totalService.getCurrencyTotal(firstCurrencyId);
        final BigDecimal secondTotalAfter = totalService.getCurrencyTotal(secondCurrencyId);
        Assert.assertEquals("", firstTotalAfter, firstTotalBefore.subtract(firstDiff));
        Assert.assertEquals("", secondTotalAfter, secondTotalBefore.add(secondDiff));

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        final BigDecimal rate = currencyHelper.getRate(firstCurrencyId);
        final BigDecimal expected = secondDiff.subtract(firstDiff.multiply(rate));
        Assert.assertEquals("", defaultBefore.add(expected), defaultAfter);
    }

    @Test
    public void exchangeFromDefault() {
        LOGGER.debug("[TEST] exchangeFromDefault");
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();
        
        final Integer firstAccountId = accountIds.get(DefinedAccount.UGH_CASH);
        final Integer firstCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.UGH_CASH).getCurrencyId();
        final BigDecimal firstTotalBefore = totalService.getCurrencyTotal(firstCurrencyId);
        Assert.assertNotNull(firstTotalBefore);

        final Integer secondAccountId = accountIds.get(DefinedAccount.USD_CASH);
        final Integer secondCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.USD_CASH).getCurrencyId();
        final BigDecimal secondTotalBefore = totalService.getCurrencyTotal(secondCurrencyId);
        Assert.assertNotNull(secondTotalBefore);

        TransactionVO exchangeTx = new TransactionVO();
        exchangeTx.setDate(new Date());
        exchangeTx.setFirstAccount(firstAccountId);
        final BigDecimal firstDiff = BigDecimal.valueOf(8140);
        exchangeTx.setFirstAmmount(firstDiff);
        exchangeTx.setSecondAccount(secondAccountId);
        final BigDecimal secondDiff = BigDecimal.valueOf(1000);
        exchangeTx.setSecondAmmount(secondDiff);
        exchangeTx.setNote("За 8140 грн. готівкою куплено 1000 дол. готівкою");
        exchangeTx.setType(TransactionType.TRANSFER);
        transactionService.save(exchangeTx);

        final BigDecimal firstTotalAfter = totalService.getCurrencyTotal(firstCurrencyId);
        final BigDecimal secondTotalAfter = totalService.getCurrencyTotal(secondCurrencyId);
        Assert.assertEquals("", firstTotalAfter, firstTotalBefore.subtract(firstDiff));
        Assert.assertEquals("", secondTotalAfter, secondTotalBefore.add(secondDiff));

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        final BigDecimal rate = currencyHelper.getRate(secondCurrencyId);
        final BigDecimal expected = secondDiff.multiply(rate).subtract(firstDiff);
        Assert.assertEquals("", defaultBefore.add(expected), defaultAfter);
    }

    @Test
    public void crossExchange() {
        LOGGER.debug("[TEST] crossExchange");
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();

        final Integer firstAccountId = accountIds.get(DefinedAccount.EURO_CARD);
        final Integer firstCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.EURO_CARD).getCurrencyId();
        final BigDecimal firstTotalBefore = totalService.getCurrencyTotal(firstCurrencyId);
        Assert.assertNotNull(firstTotalBefore);

        final Integer secondAccountId = accountIds.get(DefinedAccount.USD_CASH);
        final Integer secondCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.USD_CASH).getCurrencyId();
        final BigDecimal secondTotalBefore = totalService.getCurrencyTotal(secondCurrencyId);
        Assert.assertNotNull(secondTotalBefore);

        TransactionVO exchangeTx = new TransactionVO();
        exchangeTx.setDate(new Date());
        exchangeTx.setFirstAccount(firstAccountId);
        final BigDecimal firstDiff = BigDecimal.valueOf(8);
        exchangeTx.setFirstAmmount(firstDiff);
        exchangeTx.setSecondAccount(secondAccountId);
        final BigDecimal secondDiff = BigDecimal.valueOf(10);
        exchangeTx.setSecondAmmount(secondDiff);
        exchangeTx.setNote("Переказ з євро в долари.");
        exchangeTx.setType(TransactionType.TRANSFER);
        transactionService.save(exchangeTx);

        final BigDecimal firstTotalAfter = totalService.getCurrencyTotal(firstCurrencyId);
        final BigDecimal secondTotalAfter = totalService.getCurrencyTotal(secondCurrencyId);
        Assert.assertEquals("", firstTotalAfter, firstTotalBefore.subtract(firstDiff));
        Assert.assertEquals("", secondTotalAfter, secondTotalBefore.add(secondDiff));

        /*final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
         Assert.assertEquals("", defaultAfter, defaultBefore.add(diff));*/
    }
    
    @Test
    public void editIncome() {
        LOGGER.debug("[TEST] editIncome");

        final Integer incomeSalaryAccountId = accountIds.get(DefinedAccount.INCOME_SALARY);
        final Integer incomeSalaryCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.INCOME_SALARY).getCurrencyId();
         
        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(incomeSalaryAccountId);
        final BigDecimal firstDiff = BigDecimal.valueOf(30);
        incomeTx.setFirstAmmount(firstDiff);
        incomeTx.setSecondAccount(accountIds.get(DefinedAccount.UGH_CASH));
        incomeTx.setNote("Зарплата за січень.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);
        
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();
        final BigDecimal totalBefore = totalService.getCurrencyTotal(incomeSalaryCurrencyId);
        Assert.assertNotNull(totalBefore);
        
        final BigDecimal secDiff = BigDecimal.valueOf(100);
        incomeTx.setFirstAmmount(secDiff);
        transactionService.save(incomeTx); 
        
        final BigDecimal totalAfter = totalService.getCurrencyTotal(incomeSalaryCurrencyId);
        Assert.assertEquals("", totalAfter, totalBefore.add(firstDiff.negate().add(secDiff)));

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals("", defaultAfter, defaultBefore.add(secDiff.add(firstDiff.negate())));
        
        
    }
    
    @Test
    public void editExpense() {
        LOGGER.debug("[TEST] editExpense");
        final Integer expenseMarketAccountId = accountIds.get(DefinedAccount.EXPENSE_MARKET);
        final Integer expenseMarketCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.EXPENSE_MARKET).getCurrencyId();
        
        TransactionVO expenseTx = new TransactionVO();
        expenseTx.setDate(new Date());
        expenseTx.setFirstAccount(expenseMarketAccountId);
        final BigDecimal firstDiff = BigDecimal.valueOf(85);
        expenseTx.setFirstAmmount(firstDiff);
        expenseTx.setSecondAccount(accountIds.get(DefinedAccount.UGH_CASH));
        expenseTx.setNote("Витрата на базарі.");
        expenseTx.setType(TransactionType.EXPENSE);
        transactionService.save(expenseTx);
        
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();
        final BigDecimal totalBefore = totalService.getCurrencyTotal(expenseMarketCurrencyId);
        Assert.assertNotNull(totalBefore);
        
        final BigDecimal secDiff = BigDecimal.valueOf(100);  
        expenseTx.setFirstAmmount(secDiff);
        transactionService.save(expenseTx);
        
        final BigDecimal totalAfter = totalService.getCurrencyTotal(expenseMarketCurrencyId);
        Assert.assertEquals("", totalAfter, totalBefore.subtract(firstDiff.negate().add(secDiff)));

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals("", defaultAfter, defaultBefore.subtract(firstDiff.negate().add(secDiff)));
        
    }
    
    @Test
    public void editTransfer() {
        LOGGER.debug("[TEST] editTransfer");
        Assert.fail("unrelized");
    }
    
    @Test
    public void deleteIncome() {
        LOGGER.debug("[TEST] deleteIncome");
        
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();
        final Integer incomeSalaryAccountId = accountIds.get(DefinedAccount.INCOME_SALARY);
        final Integer incomeSalaryCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.INCOME_SALARY).getCurrencyId();
        final BigDecimal totalBefore = totalService.getCurrencyTotal(incomeSalaryCurrencyId);
        Assert.assertNotNull(totalBefore);
        
        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(incomeSalaryAccountId);
        final BigDecimal diff = BigDecimal.valueOf(100);
        incomeTx.setFirstAmmount(diff);
        incomeTx.setSecondAccount(accountIds.get(DefinedAccount.UGH_CASH));
        incomeTx.setNote("Зарплата за січень.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);
                
        transactionService.delete(incomeTx.getTxId());
        Assert.assertTrue(!transactionService.getTransactions().contains(incomeTx));
        
        final BigDecimal totalAfter = totalService.getCurrencyTotal(incomeSalaryCurrencyId);
        Assert.assertEquals("", totalAfter, totalBefore);

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals("", defaultAfter, defaultBefore);
    }
    
    @Test
    public void deleteExpense() {
        LOGGER.debug("[TEST] deleteExpense");
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();

        final Integer expenseMarketAccountId = accountIds.get(DefinedAccount.EXPENSE_MARKET);
        final Integer expenseMarketCurrencyId = accountHelper.getDefinedAccount(DefinedAccount.EXPENSE_MARKET).getCurrencyId();
        final BigDecimal totalBefore = totalService.getCurrencyTotal(expenseMarketCurrencyId);
        Assert.assertNotNull(totalBefore);

        TransactionVO expenseTx = new TransactionVO();
        expenseTx.setDate(new Date());
        expenseTx.setFirstAccount(expenseMarketAccountId);
        final BigDecimal diff = BigDecimal.valueOf(85);
        expenseTx.setFirstAmmount(diff);
        expenseTx.setSecondAccount(accountIds.get(DefinedAccount.UGH_CASH));
        expenseTx.setNote("Витрата на базарі.");
        expenseTx.setType(TransactionType.EXPENSE);
        transactionService.save(expenseTx);

        transactionService.delete(expenseTx.getTxId());
        Assert.assertTrue(!transactionService.getTransactions().contains(expenseTx));
        
        final BigDecimal totalAfter = totalService.getCurrencyTotal(expenseMarketCurrencyId);
        Assert.assertEquals("", totalAfter, totalBefore);

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals("", defaultAfter, defaultBefore);
    }
    
    @Test
    public void deleteTransfer() {
        LOGGER.debug("[TEST] deleteTransfer");
        final BigDecimal defaultBefore = totalService.getDefaultCurrencyTotal();

        final Integer firstAccountId = accountIds.get(DefinedAccount.UGH_CASH);
        final Integer secondAccountId = accountIds.get(DefinedAccount.UGH_CARD);
        final Integer currencyId = currencyHelper.getDefinedCurrency(DefinedCurrency.UGH).getCurrencyId();
        final BigDecimal totalBefore = totalService.getCurrencyTotal(currencyId);
        Assert.assertNotNull(totalBefore);

        TransactionVO transferTx = new TransactionVO();
        transferTx.setDate(new Date());
        transferTx.setFirstAccount(firstAccountId);
        final BigDecimal diff = BigDecimal.valueOf(100);
        transferTx.setFirstAmmount(diff);
        transferTx.setSecondAccount(secondAccountId);
        transferTx.setNote("Переказ гривень з готівки на карточку.");
        transferTx.setType(TransactionType.TRANSFER);
        transactionService.save(transferTx);
        
        transactionService.delete(transferTx.getTxId());
        Assert.assertTrue(!transactionService.getTransactions().contains(transferTx));

        final BigDecimal totalAfter = totalService.getCurrencyTotal(currencyId);
        Assert.assertEquals("", totalAfter, totalBefore);

        final BigDecimal defaultAfter = totalService.getDefaultCurrencyTotal();
        Assert.assertEquals("", defaultAfter, defaultBefore);
    }
}
