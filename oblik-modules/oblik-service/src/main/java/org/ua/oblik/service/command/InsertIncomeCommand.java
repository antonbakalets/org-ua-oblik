package org.ua.oblik.service.command;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;

public class InsertIncomeCommand extends AbstractInsertCommand {

    @Override
    protected void doInsert(Txaction txaction, Account credit, Account debit) {
        txaction.setDebetAmount(tvo.getFirstAmount());
        debit.setTotal(debit.getTotal().add(tvo.getFirstAmount()));
    }
}
