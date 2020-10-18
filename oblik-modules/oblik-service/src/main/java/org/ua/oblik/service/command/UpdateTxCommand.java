package org.ua.oblik.service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.TransactionVO;

import java.math.BigDecimal;

abstract class UpdateTxCommand extends AbstractTxCommand {

    protected abstract void doUpdate(TransactionVO tvo, Txaction txaction, Account newCreditAccount, Account newDebitAccount);

    UpdateTxCommand(TransactionVO tvo) {
        super(tvo);
    }

    @Override
    public void execute() {
        Txaction txaction = txactionDao.findById(tvo.getTxId()).get();
        Account newCreditAccount = accountDao.findById(tvo.getFirstAccount()).get();
        Account newDebitAccount = accountDao.findById(tvo.getSecondAccount()).get();
        doUpdate(tvo, txaction, newCreditAccount, newDebitAccount);
        txaction.setCredit(newCreditAccount);
        txaction.setDebet(newDebitAccount);
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
    }

    @Component
    @Scope("prototype")
    static class UpdateExpenseCommand extends UpdateTxCommand {

        UpdateExpenseCommand(TransactionVO tvo) {
            super(tvo);
        }

        @Override
        protected void doUpdate(TransactionVO tvo, Txaction txaction, Account newCreditAccount, Account newDebitAccount) {
            final Account oldCreditAccount = txaction.getCredit();
            if (oldCreditAccount.equals(newCreditAccount)) {
                BigDecimal expenseDiff = txaction.getCreditAmount().subtract(tvo.getFirstAmount());
                newCreditAccount.setTotal(newCreditAccount.getTotal().add(expenseDiff));
            } else {
                oldCreditAccount.setTotal(oldCreditAccount.getTotal().add(txaction.getCreditAmount()));
                newCreditAccount.setTotal(newCreditAccount.getTotal().subtract(tvo.getFirstAmount()));
            }
            txaction.setCreditAmount(tvo.getFirstAmount());
        }
    }

    @Component
    @Scope("prototype")
    static class UpdateIncomeCommand extends UpdateTxCommand {

        UpdateIncomeCommand(TransactionVO tvo) {
            super(tvo);
        }

        @Override
        protected void doUpdate(TransactionVO tvo, Txaction txaction, Account newCreditAccount, Account newDebitAccount) {
            final Account oldDebetAccount = txaction.getDebet();
            if (oldDebetAccount.equals(newDebitAccount)) {
                BigDecimal incomeDiff = txaction.getDebetAmount().subtract(tvo.getFirstAmount());
                newDebitAccount.setTotal(newDebitAccount.getTotal().subtract(incomeDiff));
            } else {
                oldDebetAccount.setTotal(oldDebetAccount.getTotal().subtract(txaction.getDebetAmount()));
                newDebitAccount.setTotal(newDebitAccount.getTotal().add(tvo.getFirstAmount()));
            }
            txaction.setDebetAmount(tvo.getFirstAmount());
        }
    }

    @Component
    @Scope("prototype")
    static class UpdateTransferCommand extends UpdateTxCommand {

        UpdateTransferCommand(TransactionVO tvo) {
            super(tvo);
        }

        @Override
        protected void doUpdate(TransactionVO tvo, Txaction txaction, Account newCreditAccount, Account newDebitAccount) {
            // in case currency is the same - the amount is the same
            if (newCreditAccount.getCurrency().equals(newDebitAccount.getCurrency())) {
                tvo.setSecondAmount(tvo.getFirstAmount());
            }
            final Account oldDebetAccount = txaction.getDebet();
            if (oldDebetAccount.equals(newDebitAccount)) {
                BigDecimal debetDiff = txaction.getDebetAmount().subtract(tvo.getSecondAmount());
                newDebitAccount.setTotal(newDebitAccount.getTotal().subtract(debetDiff));
            } else {
                oldDebetAccount.setTotal(oldDebetAccount.getTotal().subtract(txaction.getDebetAmount()));
                newDebitAccount.setTotal(newDebitAccount.getTotal().add(tvo.getSecondAmount()));
            }
            final Account oldCreditAccount = txaction.getCredit();
            if (oldCreditAccount.equals(newCreditAccount)) {
                BigDecimal creditDiff = txaction.getCreditAmount().subtract(tvo.getFirstAmount());
                newCreditAccount.setTotal(newCreditAccount.getTotal().add(creditDiff));
            } else {
                oldCreditAccount.setTotal(oldCreditAccount.getTotal().add(txaction.getCreditAmount()));
                newCreditAccount.setTotal(newCreditAccount.getTotal().subtract(tvo.getFirstAmount()));
            }
            txaction.setCreditAmount(tvo.getFirstAmount());
            txaction.setDebetAmount(tvo.getSecondAmount());
        }
    }
}
