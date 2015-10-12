package org.ua.oblik.service.command;

import java.math.BigDecimal;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.TransactionVO;

public class UpdateTransferCommand extends AbstractUpdateCommand {

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
            accountDao.update(oldDebetAccount);
        }
        final Account oldCreditAccount = txaction.getCredit();
        if (oldCreditAccount.equals(newCreditAccount)) {
            BigDecimal creditDiff = txaction.getCreditAmount().subtract(tvo.getFirstAmount());
            newCreditAccount.setTotal(newCreditAccount.getTotal().add(creditDiff));
        } else {
            oldCreditAccount.setTotal(oldCreditAccount.getTotal().add(txaction.getCreditAmount()));
            newCreditAccount.setTotal(newCreditAccount.getTotal().subtract(tvo.getFirstAmount()));
            accountDao.update(oldCreditAccount);
        }
        txaction.setCreditAmount(tvo.getFirstAmount());
        txaction.setDebetAmount(tvo.getSecondAmount());
        accountDao.update(newCreditAccount);
        accountDao.update(newDebitAccount);
    }
}
