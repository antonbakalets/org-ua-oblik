package org.ua.oblik.service.command;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;

public class InsertExpenseCommand extends AbstractInsertCommand {
    @Override
    protected void doInsert(Txaction txaction, Account credit, Account debit) {
        txaction.setCreditAmount(tvo.getFirstAmount());
        credit.setTotal(credit.getTotal().subtract(tvo.getFirstAmount()));
        accountDao.update(credit);
    }
}
