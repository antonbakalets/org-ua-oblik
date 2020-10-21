package org.ua.oblik.service.command;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ua.oblik.domain.model.AccountKind;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.TransactionVO;

@Component
@Scope("prototype")
class DeleteTxCommand extends AbstractTxCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTxCommand.class);

    DeleteTxCommand(TransactionVO tvo) {
        super(tvo);
    }

    @Override
    public void execute() {
        Integer transactionId = tvo.getTxId();
        Txaction txaction = txactionRepository.findById(transactionId).get();
        final Account credit = txaction.getCredit();
        final Account debet = txaction.getDebet();

        if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.INCOME) {
            debet.setTotal(debet.getTotal().subtract(txaction.getDebetAmount()));
        } else if (credit.getKind() == AccountKind.ASSETS && debet.getKind() == AccountKind.EXPENSE) {
            credit.setTotal(credit.getTotal().add(txaction.getCreditAmount()));
        } else if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.ASSETS) {
            final BigDecimal debetDiff = debet.getTotal().subtract(txaction.getCreditAmount());
            final BigDecimal creditDiff = credit.getTotal().add(txaction.getDebetAmount());
            debet.setTotal(debetDiff);
            credit.setTotal(creditDiff);
        } else {
            RuntimeException re = new IllegalArgumentException("Cannot determine transaction type, id :" + transactionId);
            LOGGER.error("Cannot determine transaction type.", re);
            throw re;
        }
        txactionRepository.delete(txaction);
    }
}
