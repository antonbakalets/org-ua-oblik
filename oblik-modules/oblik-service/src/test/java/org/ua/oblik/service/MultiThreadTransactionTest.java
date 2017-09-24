package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;
import org.ua.oblik.service.test.DefinedAccount;

public class MultiThreadTransactionTest extends BaseTransactionServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadTransactionTest.class);

    public static final int THREADS_POOL_SIZE = 1;
    public static final int TEST_RUN_NUMBER = 100;

    @Test
    public void exec() {
        long timeMillis = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_POOL_SIZE);
        LOGGER.debug("[TEST] MultiThreadTransactionTest started.");
        defaultBefore();
        for (int i = 0; i < TEST_RUN_NUMBER; i++) {
            Runnable worker = new CoinWorkerThread();
            executorService.execute(worker);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        LOGGER.debug("[TEST] MultiThreadTransactionTest took {} ms.", System.currentTimeMillis() - timeMillis);
        defaultAfter(defaultCurrencyTotal);
    }

    private class CoinWorkerThread implements Runnable {
        @Override
        public void run() {
            TransactionVO incomeTx = new TransactionVO();
            incomeTx.setDate(new Date());
            incomeTx.setFirstAccount(aH.accountId(DefinedAccount.INCOME_SALARY));
            final BigDecimal diff = BigDecimal.valueOf(125);
            incomeTx.setFirstAmount(diff);
            incomeTx.setSecondAccount(aH.accountId(DefinedAccount.UGH_CASH));
            incomeTx.setNote("Зарплата 125 грн. зарахована до готівки.");
            incomeTx.setType(TransactionType.INCOME);
            try {
                transactionService.save(incomeTx);
            } catch (Exception e) {
                LOGGER.debug("Ignored.", e);
            }

            TransactionVO exchangeTx = new TransactionVO();
            exchangeTx.setDate(new Date());
            exchangeTx.setFirstAccount(aH.accountId(DefinedAccount.UGH_CASH));
            final BigDecimal firstDiff = BigDecimal.valueOf(125);
            exchangeTx.setFirstAmount(firstDiff);
            exchangeTx.setSecondAccount(aH.accountId(DefinedAccount.USD_CASH));
            final BigDecimal secondDiff = BigDecimal.valueOf(2);
            exchangeTx.setSecondAmount(secondDiff);
            exchangeTx.setNote("За 125 грн. готівкою куплено 2 дол. готівкою");
            exchangeTx.setType(TransactionType.TRANSFER);
            try {
                transactionService.save(exchangeTx);
            } catch (Exception e) {
                LOGGER.debug("Ignored.", e);
            }

            TransactionVO expenseTx = new TransactionVO();
            expenseTx.setDate(new Date());
            expenseTx.setFirstAccount(aH.accountId(DefinedAccount.USD_CASH));
            final BigDecimal expenseDiff = BigDecimal.valueOf(2);
            expenseTx.setFirstAmount(expenseDiff);
            expenseTx.setSecondAccount(aH.accountId(DefinedAccount.EXPENSE_IN_DOLLAR));
            expenseTx.setNote("Витрата 2 долара на базарі.");
            expenseTx.setType(TransactionType.EXPENSE);
            try {
                transactionService.save(expenseTx);
            } catch (Exception e) {
                LOGGER.debug("Ignored.", e);
            }
        }
    }
}
