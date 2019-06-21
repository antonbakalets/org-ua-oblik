package org.ua.oblik.service.command;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;

abstract class AbstractInsertCommand extends AbstractTxCommand {

    protected abstract void doInsert(Txaction txaction, Account credit, Account debit);

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
        txactionDao.insert(txaction);
        tvo.setTxId(txaction.getId());
    }
}
