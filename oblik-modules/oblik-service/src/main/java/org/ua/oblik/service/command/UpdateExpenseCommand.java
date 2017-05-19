package org.ua.oblik.service.command;

import java.math.BigDecimal;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.TransactionVO;

public class UpdateExpenseCommand extends AbstractUpdateCommand {

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
