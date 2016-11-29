package org.ua.oblik.service.command;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.TransactionVO;

abstract class AbstractUpdateCommand extends AbstractTxCommand {

    protected abstract void doUpdate(TransactionVO tvo, Txaction txaction, Account newCreditAccount, Account newDebitAccount);

    @Override
    public void execute() {
        Txaction txaction = txactionDao.select(tvo.getTxId());
        Account newCreditAccount = accountDao.select(tvo.getFirstAccount());
        Account newDebitAccount = accountDao.select(tvo.getSecondAccount());
        doUpdate(tvo, txaction, newCreditAccount, newDebitAccount);
        txaction.setCredit(newCreditAccount);
        txaction.setDebet(newDebitAccount);
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
    }
}
