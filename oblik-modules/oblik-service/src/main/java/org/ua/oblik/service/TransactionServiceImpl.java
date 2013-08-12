package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        Txaction txaction = new Txaction();
        Account credit = accountDao.select(tvo.getFirstAccount());
        Account debet = accountDao.select(tvo.getSecondAccount());
        switch (tvo.getType()) {
            case INCOME:                
                txaction.setDebetAmmount(tvo.getFirstAmmount());
                //firstAccount.setTotal(firstAccount.getTotal().add(tvo.getFirstAmmount()));
                //accountDao.update(firstAccount);
                debet.setTotal(debet.getTotal().add(tvo.getFirstAmmount()));
                accountDao.update(debet);
                break;
            case EXPENSE:
                txaction.setCreditAmmount(tvo.getFirstAmmount());
                credit.setTotal(credit.getTotal().subtract(tvo.getFirstAmmount()));
                accountDao.update(credit);
                //secondAccount.setTotal(secondAccount.getTotal().add(tvo.getFirstAmmount()));
                //accountDao.update(secondAccount);
                break;
            case TRANSFER:
                // TODO implement different currencies
                if (credit.getCurrency().equals(debet.getCurrency())) { // same currency - one ammount
                    tvo.setSecondAmmount(tvo.getFirstAmmount());
                }
                txaction.setDebetAmmount(tvo.getSecondAmmount());
                txaction.setCreditAmmount(tvo.getFirstAmmount());
                BigDecimal firstTotal = credit.getTotal().subtract(tvo.getFirstAmmount());
                credit.setTotal(firstTotal);
                BigDecimal secondTotal = debet.getTotal().add(tvo.getSecondAmmount());
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
        Account credit = accountDao.select(tvo.getFirstAccount());
        Account debet = accountDao.select(tvo.getSecondAccount());
        switch (tvo.getType()) {
            case INCOME:
                BigDecimal incomeDiff = txaction.getDebetAmmount().subtract(tvo.getFirstAmmount());
                txaction.setDebetAmmount(tvo.getFirstAmmount());
                //firstAccount.setTotal(firstAccount.getTotal().add(incomeDiff));
                //accountDao.update(firstAccount);
                debet.setTotal(debet.getTotal().subtract(incomeDiff));
                accountDao.update(debet);
                break;
            case EXPENSE:
                BigDecimal expenseDiff = txaction.getCreditAmmount().subtract(tvo.getFirstAmmount());
                txaction.setCreditAmmount(tvo.getFirstAmmount());
                credit.setTotal(credit.getTotal().add(expenseDiff));
                accountDao.update(credit);
                //secondAccount.setTotal(secondAccount.getTotal().add(expenseDiff));
                //accountDao.update(secondAccount);
                break;
            case TRANSFER:
                if (credit.getCurrency().equals(debet.getCurrency())) { // same currency - one ammount
                    tvo.setSecondAmmount(tvo.getFirstAmmount());
                }
                BigDecimal creditDiff = txaction.getCreditAmmount().subtract(tvo.getFirstAmmount());
                BigDecimal debetDiff = txaction.getDebetAmmount().subtract(tvo.getSecondAmmount());
                txaction.setCreditAmmount(tvo.getFirstAmmount());
                txaction.setDebetAmmount(tvo.getSecondAmmount());
                credit.setTotal(credit.getTotal().add(creditDiff));
                debet.setTotal(debet.getTotal().subtract(debetDiff));
                accountDao.update(credit);
                accountDao.update(debet);
                break;
        }
        txaction.setCredit(credit);
        txaction.setDebet(debet);
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
        txactionDao.update(txaction);
    }

    @Transactional
    @Override
    public void delete(Integer transactionId) {
        Txaction txaction = txactionDao.select(transactionId);
        final Account credit = txaction.getCredit();
        final Account debet = txaction.getDebet();

        if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.INCOME) {
            debet.setTotal(debet.getTotal().subtract(txaction.getDebetAmmount()));
            accountDao.update(debet);
        } else if (credit.getKind() == AccountKind.ASSETS && debet.getKind() == AccountKind.EXPENSE) {
            credit.setTotal(credit.getTotal().add(txaction.getCreditAmmount()));
            accountDao.update(credit);
        } else if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.ASSETS) {
            final BigDecimal debetDiff = debet.getTotal().subtract(txaction.getCreditAmmount());
            final BigDecimal creditDiff = credit.getTotal().add(txaction.getDebetAmmount());
            debet.setTotal(debetDiff);
            credit.setTotal(creditDiff);
            accountDao.update(debet);
            accountDao.update(credit);
        } else {
            RuntimeException re = new IllegalArgumentException("Cannot determine transaction type, id :" + transactionId);
            LOG.error("Cannot determine transaction type.", re);
            throw re;
        }
        txactionDao.delete(txaction);
    }
    
    

    @Override
	public List<TransactionVO> sortTransactionsByDate(
			List<TransactionVO> transactions) {
		
    	if (transactions.size() > 0) {
    		Collections.sort(transactions, new Comparator<TransactionVO>() {
    			@Override
    		    public int compare(final TransactionVO object1, final TransactionVO object2) {
    				return object1.getDate().compareTo(object2.getDate());
    			}
    		} );
    	}
		return transactions;
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
            result.setFirstAmmount(model.getDebetAmmount());
            result.setSecondAccount(credit.getId());
        } else if (credit.getKind() == AccountKind.ASSETS && debet.getKind() == AccountKind.EXPENSE) {
            result.setType(TransactionType.EXPENSE);
            result.setFirstAccount(credit.getId());
            result.setFirstAmmount(model.getCreditAmmount());
            result.setSecondAccount(debet.getId());
        } else if (debet.getKind() == AccountKind.ASSETS && credit.getKind() == AccountKind.ASSETS) {
            result.setType(TransactionType.TRANSFER);
            result.setFirstAccount(credit.getId());
            result.setFirstAmmount(model.getCreditAmmount());
            result.setSecondAccount(debet.getId());
            result.setSecondAmmount(model.getDebetAmmount());
        } else {
            RuntimeException re = new IllegalArgumentException("Cannot determine transaction type, id :" + model.getId());
            LOG.error("Cannot determine transaction type.", re);
            throw re;
        }
        result.setDate(model.getTxDate());
        result.setNote(model.getComment());
        return result;
    }

    private static List<TransactionVO> convert(List<Txaction> modelList) {
        List<TransactionVO> result = new ArrayList<>(modelList.size());
        for (Txaction model : modelList) {
            result.add(convert(model));
        }
        return result;
    }
}
