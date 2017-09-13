package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;
import org.ua.oblik.service.test.DefinedAccount;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionServiceTest extends BaseTransactionServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceTest.class);

    @Test
    public void insertIncome() {
        LOGGER.debug("[TEST] insertIncome");
        defaultBefore();
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);

        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(aH.accountId(DefinedAccount.INCOME_SALARY));
        final BigDecimal diff = BigDecimal.valueOf(125);
        incomeTx.setFirstAmount(diff);
        incomeTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_CASH));
        incomeTx.setNote("Зарплата 125 грн. зарахована до готівки.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);

        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.add(diff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.add(diff));

        defaultAfter(defaultCurrencyTotal.add(diff));
    }
 
    /*@Test
    public void negativeIncome() {
        LOGGER.debug("[TEST] negativeIncome");
        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(accountIds.get(DefinedAccount.INCOME_FOUND_USD));
        incomeTx.setFirstAmount(BigDecimal.valueOf(-85));
        incomeTx.setSecondAccount(accountIds.get(DefinedAccount.USD_CASH));
        incomeTx.setNote("Нонсенс. Зарплата -85 грн. зарахована до готівки в доларах.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);
        Assert.fail("Test should not pass. Different accounts currencies.");
    }*/

    @Test
    public void insertExpense() {
        LOGGER.debug("[TEST] insertExpense");
        defaultBefore();
        
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);

        TransactionVO expenseTx = new TransactionVO();
        expenseTx.setDate(new Date());
        expenseTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal diff = BigDecimal.valueOf(65);
        expenseTx.setFirstAmount(diff);
        expenseTx.setSecondAccount(aH.accountId(DefinedAccount.EXPENSE_MARKET));
        expenseTx.setNote("Витрата 65 грн. із готівки на базар.");
        expenseTx.setType(TransactionType.EXPENSE);
        transactionService.save(expenseTx);

        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.subtract(diff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.subtract(diff));

        defaultAfter(defaultCurrencyTotal.subtract(diff));
    }

    @Test
    public void insertTransfer() {
        LOGGER.debug("[TEST] insertTransfer");
        defaultBefore();
        
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        final BigDecimal cardBefore = totalByAccount(DefinedAccount.UGH_CARD);

        TransactionVO transferTx = new TransactionVO();
        transferTx.setDate(new Date());
        transferTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal diff = BigDecimal.valueOf(200);
        transferTx.setFirstAmount(diff);
        transferTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_CARD));
        transferTx.setNote("Переказ 200 гривень з готівки на карточку.");
        transferTx.setType(TransactionType.TRANSFER);
        transactionService.save(transferTx);

        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore); // transfer in same currency
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.subtract(diff));
        final BigDecimal cardAfter = totalByAccount(DefinedAccount.UGH_CARD);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cardAfter, cardBefore.add(diff));

        defaultAfter(defaultCurrencyTotal);
    }

    @Test
    public void exchangeToDefault() {
        LOGGER.debug("[TEST] exchangeToDefault");
        defaultBefore();
        
        final BigDecimal euroBefore = totalByCurrency(DefinedAccount.EURO_CARD);
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_DEPOSIT);
        final BigDecimal cardBefore = totalByAccount(DefinedAccount.EURO_CARD);
        final BigDecimal depoBefore = totalByAccount(DefinedAccount.UGH_DEPOSIT);

        TransactionVO exchangeTx = new TransactionVO();
        exchangeTx.setDate(new Date());
        exchangeTx.setFirstAccount(aH.accountId(DefinedAccount.EURO_CARD));
        final BigDecimal firstDiff = BigDecimal.valueOf(10);
        exchangeTx.setFirstAmount(firstDiff);
        exchangeTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_DEPOSIT));
        final BigDecimal secondDiff = BigDecimal.valueOf(107);
        exchangeTx.setSecondAmount(secondDiff);
        exchangeTx.setNote("Обмін 10 євро з карточки в 107 гривень на депозиті.");
        exchangeTx.setType(TransactionType.TRANSFER);
        transactionService.save(exchangeTx);
        
        final BigDecimal euroAfter = totalByCurrency(DefinedAccount.EURO_CARD);
        Assert.assertEquals("Невідповідність у кількості євро.", euroAfter, euroBefore.subtract(firstDiff));
        final BigDecimal cardAfter = totalByAccount(DefinedAccount.EURO_CARD);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cardAfter, cardBefore.subtract(firstDiff));
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_DEPOSIT);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.add(secondDiff));
        final BigDecimal depoAfter = totalByAccount(DefinedAccount.UGH_DEPOSIT);
        Assert.assertEquals("Невідповідний залишок на рахунку.", depoAfter, depoBefore.add(secondDiff));

        defaultAfter(defaultCurrencyTotal
                .subtract(firstDiff.multiply(aH.rate(DefinedAccount.EURO_CARD)))
                .add(secondDiff));
    }

    @Test
    public void exchangeFromDefault() {
        LOGGER.debug("[TEST] exchangeFromDefault");
        defaultBefore();
        
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal usdBefore = totalByCurrency(DefinedAccount.USD_CASH);
        final BigDecimal uaCashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        final BigDecimal usCashBefore = totalByAccount(DefinedAccount.USD_CASH);
        
        TransactionVO exchangeTx = new TransactionVO();
        exchangeTx.setDate(new Date());
        exchangeTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal firstDiff = BigDecimal.valueOf(8140);
        exchangeTx.setFirstAmount(firstDiff);
        exchangeTx.setSecondAccount(aH.accountId(DefinedAccount.USD_CASH));
        final BigDecimal secondDiff = BigDecimal.valueOf(1000);
        exchangeTx.setSecondAmount(secondDiff);
        exchangeTx.setNote("За 8140 грн. готівкою куплено 1000 дол. готівкою");
        exchangeTx.setType(TransactionType.TRANSFER);
        transactionService.save(exchangeTx);

        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.subtract(firstDiff));
        final BigDecimal uaCashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", uaCashAfter, uaCashBefore.subtract(firstDiff));
        final BigDecimal usdAfter = totalByCurrency(DefinedAccount.USD_CASH);
        Assert.assertEquals("Невідповідність у кількості доларів.", usdAfter, usdBefore.add(secondDiff));
        final BigDecimal usCashAfter = totalByAccount(DefinedAccount.USD_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", usCashAfter, usCashBefore.add(secondDiff));

        defaultAfter(defaultCurrencyTotal
                .subtract(firstDiff)
                .add(secondDiff.multiply(aH.rate(DefinedAccount.USD_CASH))));
    }

    @Test
    public void crossExchange() {
        LOGGER.debug("[TEST] crossExchange");
        defaultBefore();
        
        final BigDecimal eurBefore = totalByCurrency(DefinedAccount.EURO_CARD);
        final BigDecimal usdBefore = totalByCurrency(DefinedAccount.USD_CASH);
        final BigDecimal cardBefore = totalByAccount(DefinedAccount.EURO_CARD);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.USD_CASH);
        
        TransactionVO exchangeTx = new TransactionVO();
        exchangeTx.setDate(new Date());
        exchangeTx.setFirstAccount(aH.accountId(DefinedAccount.EURO_CARD));
        final BigDecimal firstDiff = BigDecimal.valueOf(8);
        exchangeTx.setFirstAmount(firstDiff);
        exchangeTx.setSecondAccount(aH.accountId(DefinedAccount.USD_CASH));
        final BigDecimal secondDiff = BigDecimal.valueOf(10);
        exchangeTx.setSecondAmount(secondDiff);
        exchangeTx.setNote("Переказ з євро в долари.");
        exchangeTx.setType(TransactionType.TRANSFER);
        transactionService.save(exchangeTx);
         
        final BigDecimal eurAfter = totalByCurrency(DefinedAccount.EURO_CARD);
        Assert.assertEquals("Невідповідність у кількості гривні.", eurAfter, eurBefore.subtract(firstDiff));
        final BigDecimal cardAfter = totalByAccount(DefinedAccount.EURO_CARD);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cardAfter, cardBefore.subtract(firstDiff));
        final BigDecimal usdAfter = totalByCurrency(DefinedAccount.USD_CASH);
        Assert.assertEquals("Невідповідність у кількості доларів.", usdAfter, usdBefore.add(secondDiff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.USD_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.add(secondDiff));

        defaultAfter(defaultCurrencyTotal
                .subtract(firstDiff.multiply(aH.rate(DefinedAccount.EURO_CARD)))
                .add(secondDiff.multiply(aH.rate(DefinedAccount.USD_CASH))));
    }
    
    @Test
    public void editIncome() {
        LOGGER.debug("[TEST] editIncome");
        
        defaultBefore();
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        
        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(aH.accountId(DefinedAccount.INCOME_SALARY));
        final BigDecimal firstDiff = BigDecimal.valueOf(30);
        incomeTx.setFirstAmount(firstDiff);
        incomeTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_CASH));
        incomeTx.setNote("Зарплата за січень.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);
                
        final BigDecimal secDiff = BigDecimal.valueOf(100);
        incomeTx.setFirstAmount(secDiff);
        transactionService.save(incomeTx); 
        
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.add(secDiff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.add(secDiff));

        defaultAfter(defaultCurrencyTotal.add(secDiff));
    }
    
    @Test
    public void editIncomeAccountChange() {
        LOGGER.debug("[TEST] editIncomeAccountChange");
        
        defaultBefore();
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        final BigDecimal cardBefore = totalByAccount(DefinedAccount.UGH_CARD);
        
        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(aH.accountId(DefinedAccount.INCOME_SALARY));
        final BigDecimal firstDiff = BigDecimal.valueOf(30);
        incomeTx.setFirstAmount(firstDiff);
        incomeTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_CASH));
        incomeTx.setNote("Зарплата за січень.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);
                
        final BigDecimal secDiff = BigDecimal.valueOf(100);
        incomeTx.setFirstAmount(secDiff);
        incomeTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_CARD));
        transactionService.save(incomeTx); 
        
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.add(secDiff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore);
        final BigDecimal cardAfter = totalByAccount(DefinedAccount.UGH_CARD);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cardAfter, cardBefore.add(secDiff));

        defaultAfter(defaultCurrencyTotal.add(secDiff));
    }
    
    @Test
    public void editExpense() {
        LOGGER.debug("[TEST] editExpense");
        defaultBefore();
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        
        TransactionVO expenseTx = new TransactionVO();
        expenseTx.setDate(new Date());
        expenseTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal diff = BigDecimal.valueOf(85);
        expenseTx.setFirstAmount(diff);
        expenseTx.setSecondAccount(aH.accountId(DefinedAccount.EXPENSE_MARKET));
        expenseTx.setNote("Витрата на базарі.");
        expenseTx.setType(TransactionType.EXPENSE);
        transactionService.save(expenseTx);
        
        final BigDecimal secDiff = BigDecimal.valueOf(100);  
        expenseTx.setFirstAmount(secDiff);
        transactionService.save(expenseTx);
        
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.subtract(secDiff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.subtract(secDiff));

        defaultAfter(defaultCurrencyTotal.subtract(secDiff));
    }
    
    @Test
    public void editExpenseAccountChange() {
        LOGGER.debug("[TEST] editExpenseAccountChange");
        defaultBefore();
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        final BigDecimal cardBefore = totalByAccount(DefinedAccount.UGH_CARD);
        
        TransactionVO expenseTx = new TransactionVO();
        expenseTx.setDate(new Date());
        expenseTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal diff = BigDecimal.valueOf(85);
        expenseTx.setFirstAmount(diff);
        expenseTx.setSecondAccount(aH.accountId(DefinedAccount.EXPENSE_MARKET));
        expenseTx.setNote("Витрата на базарі.");
        expenseTx.setType(TransactionType.EXPENSE);
        transactionService.save(expenseTx);
        
        final BigDecimal secDiff = BigDecimal.valueOf(22);  
        expenseTx.setFirstAmount(secDiff);
        expenseTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CARD));
        transactionService.save(expenseTx);
        
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.subtract(secDiff));
         final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore);
        final BigDecimal cardAfter = totalByAccount(DefinedAccount.UGH_CARD);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cardAfter, cardBefore.subtract(secDiff));

        defaultAfter(defaultCurrencyTotal.subtract(secDiff));
    }
    
    @Test
    public void editTransfer() {
        LOGGER.debug("[TEST] editTransfer");
        defaultBefore();
        
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal eurBefore = totalByCurrency(DefinedAccount.EURO_CARD);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        final BigDecimal cardBefore = totalByAccount(DefinedAccount.EURO_CARD);
        
        TransactionVO transferTx = new TransactionVO();
        transferTx.setDate(new Date());
        transferTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal ughDiff = BigDecimal.valueOf(1050);
        transferTx.setFirstAmount(ughDiff);
        transferTx.setSecondAccount(aH.accountId(DefinedAccount.EURO_CARD));
        final BigDecimal euroDiff = BigDecimal.valueOf(99);
        transferTx.setSecondAmount(euroDiff);
        transferTx.setNote("Купуємо за 1050 грн 99 еуро.");
        transferTx.setType(TransactionType.TRANSFER);
        transactionService.save(transferTx);
        
        transferTx.setNote("передумали - купуємо за 425 грн 49 еуро");
        final BigDecimal secUghDiff = BigDecimal.valueOf(425);
        final BigDecimal secEuroDiff = BigDecimal.valueOf(49);
        transferTx.setFirstAmount(secUghDiff);
        transferTx.setSecondAmount(secEuroDiff);
        transactionService.save(transferTx);
        
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.subtract(secUghDiff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.subtract(secUghDiff));
        final BigDecimal eurAfter = totalByCurrency(DefinedAccount.EURO_CARD);
        Assert.assertEquals("Невідповідність у кількості євро.", eurAfter, eurBefore.add(secEuroDiff));
        final BigDecimal cardAfter = totalByAccount(DefinedAccount.EURO_CARD);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cardAfter, cardBefore.add(secEuroDiff));

        defaultAfter(defaultCurrencyTotal
                .subtract(secUghDiff)
                .add(secEuroDiff.multiply(aH.rate(DefinedAccount.EURO_CARD))));
    }
    
    @Test
    public void editTransferAccountChange() {
        LOGGER.debug("[TEST] editTransferAccountChange");
        defaultBefore();
        
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_DEPOSIT);
        final BigDecimal depoBefore = totalByAccount(DefinedAccount.UGH_DEPOSIT);
        final BigDecimal eurBefore = totalByCurrency(DefinedAccount.EURO_CARD);
        final BigDecimal cardBefore = totalByAccount(DefinedAccount.EURO_CARD);
        final BigDecimal usdBefore = totalByCurrency(DefinedAccount.USD_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.USD_CASH);
        
        TransactionVO transferTx = new TransactionVO();
        transferTx.setDate(new Date());
        transferTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_DEPOSIT));
        final BigDecimal ughDiff = BigDecimal.valueOf(1050);
        transferTx.setFirstAmount(ughDiff);
        transferTx.setSecondAccount(aH.accountId(DefinedAccount.EURO_CARD));
        final BigDecimal euroDiff = BigDecimal.valueOf(99);
        transferTx.setSecondAmount(euroDiff);
        transferTx.setNote("Купуємо за 1050 грн 99 еуро.");
        transferTx.setType(TransactionType.TRANSFER);
        transactionService.save(transferTx);
        
        transferTx.setNote("передумали - купуємо за 425 грн 70 доларів, а не євро");
        final BigDecimal secUghDiff = BigDecimal.valueOf(425);
        final BigDecimal secUsdDiff = BigDecimal.valueOf(70);
        transferTx.setFirstAmount(secUghDiff);
        transferTx.setSecondAmount(secUsdDiff);
        transferTx.setSecondAccount(aH.accountId(DefinedAccount.USD_CASH));
        transactionService.save(transferTx);
        
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_DEPOSIT);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.subtract(secUghDiff));
        final BigDecimal depoAfter = totalByAccount(DefinedAccount.UGH_DEPOSIT);
        Assert.assertEquals("Невідповідний залишок на рахунку.", depoAfter, depoBefore.subtract(secUghDiff));
        final BigDecimal eurAfter = totalByCurrency(DefinedAccount.EURO_CARD);
        Assert.assertEquals("Невідповідність у кількості євро.", eurAfter, eurBefore);
        final BigDecimal cardAfter = totalByAccount(DefinedAccount.EURO_CARD);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cardAfter, cardBefore);
        final BigDecimal usdAfter = totalByCurrency(DefinedAccount.USD_CASH);
        Assert.assertEquals("Невідповідність у кількості доларів.", usdAfter, usdBefore.add(secUsdDiff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.USD_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.add(secUsdDiff));

        defaultAfter(defaultCurrencyTotal
                .subtract(secUghDiff)
                .add(secUsdDiff.multiply(aH.rate(DefinedAccount.USD_CASH))));
    }
    
    @Test
    public void deleteIncome() {
        LOGGER.debug("[TEST] deleteIncome");
        
        TransactionVO incomeTx = new TransactionVO();
        incomeTx.setDate(new Date());
        incomeTx.setFirstAccount(aH.accountId(DefinedAccount.INCOME_SALARY));
        final BigDecimal diff = BigDecimal.valueOf(150);
        incomeTx.setFirstAmount(diff);
        incomeTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_CASH));
        incomeTx.setNote("Зарплата за вересень.");
        incomeTx.setType(TransactionType.INCOME);
        transactionService.save(incomeTx);
        
        defaultBefore();
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
                
        transactionService.delete(incomeTx.getTxId());
        Assert.assertTrue(!transactionService.getTransactions().contains(incomeTx));
        
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore.subtract(diff));
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.subtract(diff));

        defaultAfter(defaultCurrencyTotal.subtract(diff));
    }
    
    @Test
    public void deleteExpense() {
        LOGGER.debug("[TEST] deleteExpense");
                
        TransactionVO expenseTx = new TransactionVO();
        expenseTx.setDate(new Date());
        expenseTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal diff = BigDecimal.valueOf(0.85);
        expenseTx.setFirstAmount(diff);
        expenseTx.setSecondAccount(aH.accountId(DefinedAccount.EXPENSE_TRANSPORT));
        expenseTx.setNote("Витрата у транспорті.");
        expenseTx.setType(TransactionType.EXPENSE);
        transactionService.save(expenseTx);

        defaultBefore();
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        
        transactionService.delete(expenseTx.getTxId());
        Assert.assertTrue(!transactionService.getTransactions().contains(expenseTx));
        
        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter.compareTo(ughBefore.add(diff)), 0);
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.add(diff));

        defaultAfter(defaultCurrencyTotal.add(diff));
    }
    
    @Test
    public void deleteTransfer() {
        LOGGER.debug("[TEST] deleteTransfer");

        TransactionVO transferTx = new TransactionVO();
        transferTx.setDate(new Date());
        transferTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
        final BigDecimal diff = BigDecimal.valueOf(1120);
        transferTx.setFirstAmount(diff);
        transferTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_CARD));
        transferTx.setNote("Переказ гривень з готівки на карточку.");
        transferTx.setType(TransactionType.TRANSFER);
        transactionService.save(transferTx);

        defaultBefore();
        final BigDecimal ughBefore = totalByCurrency(DefinedAccount.UGH_CASH);
        final BigDecimal cashBefore = totalByAccount(DefinedAccount.UGH_CASH);
        final BigDecimal cardBefore = totalByAccount(DefinedAccount.UGH_CARD);
        
        transactionService.delete(transferTx.getTxId());
        Assert.assertTrue(!transactionService.getTransactions().contains(transferTx));

        final BigDecimal ughAfter = totalByCurrency(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідність у кількості гривні.", ughAfter, ughBefore); // transfer in same currency
        final BigDecimal cashAfter = totalByAccount(DefinedAccount.UGH_CASH);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cashAfter, cashBefore.add(diff));
        final BigDecimal cardAfter = totalByAccount(DefinedAccount.UGH_CARD);
        Assert.assertEquals("Невідповідний залишок на рахунку.", cardAfter, cardBefore.subtract(diff));

        defaultAfter(defaultCurrencyTotal);
    }
}
