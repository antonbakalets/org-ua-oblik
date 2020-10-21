package org.ua.oblik.service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
        Txaction txaction = new Txaction();
        Account credit = accountRepository.findById(tvo.getFirstAccount()).get();
        Account debet = accountRepository.findById(tvo.getSecondAccount()).get();
        doInsert(txaction, credit, debet);
        txaction.setCredit(credit);
        txaction.setDebet(debet);
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
        txactionRepository.save(txaction);
        tvo.setTxId(txaction.getTxacId());
    }

    @Component
    @Scope("prototype")
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

    @Component
    @Scope("prototype")
    static class InsertTransferCommand extends InsertTxCommand {

        InsertTransferCommand(TransactionVO tvo) {
            super(tvo);
        }

        @Override
        protected void doInsert(Txaction txaction, Account credit, Account debit) {
            // is currency is the same, the amount is considered the same
            if (credit.getCurrency().getCurrId().equals(debit.getCurrency().getCurrId())) {
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

    @Component
    @Scope("prototype")
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
