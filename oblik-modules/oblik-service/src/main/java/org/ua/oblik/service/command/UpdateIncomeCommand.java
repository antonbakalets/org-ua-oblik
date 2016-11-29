package org.ua.oblik.service.command;

import java.math.BigDecimal;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.TransactionVO;

public class UpdateIncomeCommand extends AbstractUpdateCommand {

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
