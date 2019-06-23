package org.ua.oblik.service.command;

import java.math.BigDecimal;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;

public class InsertTransferCommand extends AbstractInsertCommand {

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
