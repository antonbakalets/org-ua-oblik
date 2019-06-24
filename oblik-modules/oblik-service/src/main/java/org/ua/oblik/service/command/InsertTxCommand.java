package org.ua.oblik.service.command;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.TransactionVO;

import java.math.BigDecimal;

abstract class InsertTxCommand extends AbstractTxCommand {

    protected abstract void doInsert(Txaction txaction, Account credit, Account debit);

    InsertTxCommand(TransactionVO tvo) {
        super(tvo);
    }

    @Override
    public void execute() {
        Txaction txaction = entitiesFactory.createTxactionEntity();
        Account credit = accountDao.getOne(tvo.getFirstAccount());
        Account debet = accountDao.getOne(tvo.getSecondAccount());
        doInsert(txaction, credit, debet);
        txaction.setCredit(credit);
        txaction.setDebet(debet);
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
        txactionDao.save(txaction);
        tvo.setTxId(txaction.getId());
    }

    static class InsertIncomeCommand extends InsertTxCommand {

        InsertIncomeCommand(TransactionVO tvo) {
            super(tvo);
        }

        @Override
        protected void doInsert(Txaction txaction, Account credit, Account debit) {
            txaction.setDebetAmount(tvo.getFirstAmount());
            debit.setTotal(debit.getTotal().add(tvo.getFirstAmount()));
        }
    }

    static class InsertTransferCommand extends InsertTxCommand {

        InsertTransferCommand(TransactionVO tvo) {
            super(tvo);
        }

        @Override
        protected void doInsert(Txaction txaction, Account credit, Account debit) {
            // is currency is the same, the amount is considered the same
            if (credit.getCurrency().getId().equals(debit.getCurrency().getId())) {
                tvo.setSecondAmount(tvo.getFirstAmount());
            }
            txaction.setDebetAmount(tvo.getSecondAmount());
            txaction.setCreditAmount(tvo.getFirstAmount());
            BigDecimal firstTotal = credit.getTotal().subtract(tvo.getFirstAmount());
            credit.setTotal(firstTotal);
            BigDecimal secondTotal = debit.getTotal().add(tvo.getSecondAmount());
            debit.setTotal(secondTotal);
        }
    }

    static class InsertExpenseCommand extends InsertTxCommand {

        InsertExpenseCommand(TransactionVO tvo) {
            super(tvo);
        }

        @Override
        protected void doInsert(Txaction txaction, Account credit, Account debit) {
            txaction.setCreditAmount(tvo.getFirstAmount());
            credit.setTotal(credit.getTotal().subtract(tvo.getFirstAmount()));
        }
    }
}