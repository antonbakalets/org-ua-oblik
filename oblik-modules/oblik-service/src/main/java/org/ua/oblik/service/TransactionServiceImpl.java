package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.TxactionDao;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.EntitiesFactory;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TxactionDao txactionDao;

    @Autowired
    private EntitiesFactory entitiesFactory;

    @Transactional
    @Override
    public void save(TransactionVO tvo) {
        LOG.debug("Saving " + tvo.getType() + " transaction.");
        if (tvo.getTxId() == null) {
            insert(tvo);
        } else {
            update(tvo);
        }
    }

    //@Secured
    private void insert(TransactionVO tvo) {
        Txaction txaction = entitiesFactory.createTxactionEntity();
        Account credit = accountDao.select(tvo.getFirstAccount());
        Account debet = accountDao.select(tvo.getSecondAccount());
        switch (tvo.getType()) {
            case INCOME:
                txaction.setDebetAmount(tvo.getFirstAmount());
                debet.setTotal(debet.getTotal().add(tvo.getFirstAmount()));
                accountDao.update(debet);
                break;
            case EXPENSE:
                txaction.setCreditAmount(tvo.getFirstAmount());
                credit.setTotal(credit.getTotal().subtract(tvo.getFirstAmount()));
                accountDao.update(credit);
                break;
            case TRANSFER:
                // in case currency is the same - the amount is the same
                if (credit.getCurrency().equals(debet.getCurrency())) {
                    tvo.setSecondAmount(tvo.getFirstAmount());
                }
                txaction.setDebetAmount(tvo.getSecondAmount());
                txaction.setCreditAmount(tvo.getFirstAmount());
                BigDecimal firstTotal = credit.getTotal().subtract(tvo.getFirstAmount());
                credit.setTotal(firstTotal);
                BigDecimal secondTotal = debet.getTotal().add(tvo.getSecondAmount());
                debet.setTotal(secondTotal);
                accountDao.update(credit);
                accountDao.update(debet);
                break;
        }
        txaction.setCredit(credit);
        txaction.setDebet(debet);
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
        txactionDao.insert(txaction);
        tvo.setTxId(txaction.getId());
    }

    private void update(TransactionVO tvo) {
        Txaction txaction = txactionDao.select(tvo.getTxId());
        Account newCreditAccount = accountDao.select(tvo.getFirstAccount());
        Account newDebetAccount = accountDao.select(tvo.getSecondAccount());
        switch (tvo.getType()) {
            case INCOME: {
                updateIncomeTxaction(tvo, txaction, newDebetAccount);
            }
            break;
            case EXPENSE: {
                updateExpenseTransaction(tvo, txaction, newCreditAccount);
            }
            break;
            case TRANSFER: {
                updateTransferTxaction(tvo, txaction, newCreditAccount, newDebetAccount);
            }
            break;
        }
        txaction.setCredit(newCreditAccount);
        txaction.setDebet(newDebetAccount);
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
        txactionDao.update(txaction);
    }

    private void updateIncomeTxaction(TransactionVO tvo, Txaction txaction, Account newDebetAccount) {
        final Account oldDebetAccount = txaction.getDebet();
        if (oldDebetAccount.equals(newDebetAccount)) {
            BigDecimal incomeDiff = txaction.getDebetAmount().subtract(tvo.getFirstAmount());
            newDebetAccount.setTotal(newDebetAccount.getTotal().subtract(incomeDiff));
        } else {
            oldDebetAccount.setTotal(oldDebetAccount.getTotal().subtract(txaction.getDebetAmount()));
            newDebetAccount.setTotal(newDebetAccount.getTotal().add(tvo.getFirstAmount()));
            accountDao.update(oldDebetAccount);
        }
        txaction.setDebetAmount(tvo.getFirstAmount());
        accountDao.update(newDebetAccount);
    }

    private void updateExpenseTransaction(TransactionVO tvo, Txaction txaction, Account newCreditAccount) {
        final Account oldCreditAccount = txaction.getCredit();
        if (oldCreditAccount.equals(newCreditAccount)) {
            BigDecimal expenseDiff = txaction.getCreditAmount().subtract(tvo.getFirstAmount());
            newCreditAccount.setTotal(newCreditAccount.getTotal().add(expenseDiff));
        } else {
            oldCreditAccount.setTotal(oldCreditAccount.getTotal().add(txaction.getCreditAmount()));
            newCreditAccount.setTotal(newCreditAccount.getTotal().subtract(tvo.getFirstAmount()));
            accountDao.update(oldCreditAccount);
        }
        txaction.setCreditAmount(tvo.getFirstAmount());
        accountDao.update(newCreditAccount);
    }

    private void updateTransferTxaction(TransactionVO tvo, Txaction txaction, Account newCreditAccount, Account newDebetAccount) {
        // in case currency is the same - the amount is the same
        if (newCreditAccount.getCurrency().equals(newDebetAccount.getCurrency())) {
            tvo.setSecondAmount(tvo.getFirstAmount());
        }
        final Account oldDebetAccount = txaction.getDebet();
        if (oldDebetAccount.equals(newDebetAccount)) {
            BigDecimal debetDiff = txaction.getDebetAmount().subtract(tvo.getSecondAmount());
            newDebetAccount.setTotal(newDebetAccount.getTotal().subtract(debetDiff));
        } else {
            oldDebetAccount.setTotal(oldDebetAccount.getTotal().subtract(txaction.getDebetAmount()));
            newDebetAccount.setTotal(newDebetAccount.getTotal().add(tvo.getSecondAmount()));
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
        accountDao.update(newDebetAccount);
    }

    @Override
    @Transactional
    public void delete(Integer transactionId) {
        Txaction txaction = txactionDao.select(transactionId);
        final Account credit = txaction.getCredit();
        final Account debet = txaction.getDebet();

        if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.INCOME) {
            debet.setTotal(debet.getTotal().subtract(txaction.getDebetAmount()));
            accountDao.update(debet);
        } else if (credit.getKind() == AccountKind.ASSETS && debet.getKind() == AccountKind.EXPENSE) {
            credit.setTotal(credit.getTotal().add(txaction.getCreditAmount()));
            accountDao.update(credit);
        } else if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.ASSETS) {
            final BigDecimal debetDiff = debet.getTotal().subtract(txaction.getCreditAmount());
            final BigDecimal creditDiff = credit.getTotal().add(txaction.getDebetAmount());
            debet.setTotal(debetDiff);
            credit.setTotal(creditDiff);
            accountDao.update(debet);
            accountDao.update(credit);
        } else {
            RuntimeException re = new IllegalArgumentException("Cannot determine transaction type, id :" + transactionId);
            LOG.error("Cannot determine transaction type.", re);
            throw re;
        }
        txactionDao.delete(transactionId);
    }

    @Override
    public TransactionVO getTransaction(Integer transactionId) {
        return convert(txactionDao.select(transactionId));
    }

    @Override
    public List<TransactionVO> getTransactions() {
        return convert(txactionDao.selectAll());
    }

    @Override
    public List<TransactionVO> getTransactions(Date date) {
        return convert(txactionDao.selectByMonth(date));
    }

    private static TransactionVO convert(Txaction model) {
        TransactionVO result = new TransactionVO();
        result.setTxId(model.getId());
        final Account credit = model.getCredit();
        final Account debet = model.getDebet();
        if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.INCOME) {
            result.setType(TransactionType.INCOME);
            result.setFirstAccount(debet.getId());
            result.setFirstAmount(model.getDebetAmount());
            result.setSecondAccount(credit.getId());
        } else if (credit.getKind() == AccountKind.ASSETS && debet.getKind() == AccountKind.EXPENSE) {
            result.setType(TransactionType.EXPENSE);
            result.setFirstAccount(credit.getId());
            result.setFirstAmount(model.getCreditAmount());
            result.setSecondAccount(debet.getId());
        } else if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.ASSETS) {
            result.setType(TransactionType.TRANSFER);
            result.setFirstAccount(credit.getId());
            result.setFirstAmount(model.getCreditAmount());
            result.setSecondAccount(debet.getId());
            result.setSecondAmount(model.getDebetAmount());
        } else {
            RuntimeException re = new IllegalArgumentException("Cannot determine transaction type, id :" + model.getId());
            LOG.error("Cannot determine transaction type.", re);
            throw re;
        }
        result.setDate(model.getTxDate());
        result.setNote(model.getComment());
        return result;
    }

    private static List<TransactionVO> convert(List<? extends Txaction> modelList) {
        List<TransactionVO> result = new ArrayList<>(modelList.size());
        for (Txaction model : modelList) {
            result.add(convert(model));
        }
        return result;
    }
}
