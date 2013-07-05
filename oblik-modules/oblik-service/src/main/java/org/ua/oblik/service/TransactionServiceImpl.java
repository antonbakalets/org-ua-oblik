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
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionServiceImpl implements TransactionService {
    
    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);
    
    @Autowired
    private AccountService accountService;
    
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
        Account firstAccount = accountDao.select(tvo.getFirstAccount());
        Account secondAccount = accountDao.select(tvo.getSecondAccount());
        switch (tvo.getType()) {
            case INCOME:
                txaction.setDebet(firstAccount);
                txaction.setDebetAmmount(tvo.getFirstAmmount());
                txaction.setCredit(secondAccount);
                firstAccount.setTotal(firstAccount.getTotal().add(tvo.getFirstAmmount()));
                accountDao.update(firstAccount);
                secondAccount.setTotal(secondAccount.getTotal().add(tvo.getFirstAmmount()));
                accountDao.update(secondAccount);
                break;
            case EXPENSE:
                txaction.setCredit(firstAccount);
                txaction.setCreditAmmount(tvo.getFirstAmmount());
                txaction.setDebet(secondAccount);
                firstAccount.setTotal(firstAccount.getTotal().add(tvo.getFirstAmmount()));
                accountDao.update(firstAccount);
                secondAccount.setTotal(secondAccount.getTotal().subtract(tvo.getFirstAmmount()));
                accountDao.update(secondAccount);
                break;
            case TRANSFER:
                // TODO implement different currencies
                if (firstAccount.getCurrency().equals(secondAccount.getCurrency())) { // same currency - one ammount
                    tvo.setSecondAmmount(tvo.getFirstAmmount());
                }
                txaction.setCredit(firstAccount);
                txaction.setCreditAmmount(tvo.getFirstAmmount());
                txaction.setDebet(secondAccount);
                txaction.setDebetAmmount(tvo.getSecondAmmount());
                BigDecimal firstTotal = firstAccount.getTotal().subtract(tvo.getFirstAmmount());
                firstAccount.setTotal(firstTotal);
                BigDecimal secondTotal = secondAccount.getTotal().add(tvo.getSecondAmmount());
                secondAccount.setTotal(secondTotal);
                accountDao.update(firstAccount);
                accountDao.update(secondAccount);
                break;
        }
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
        txactionDao.insert(txaction);
        tvo.setTxId(txaction.getId());
    }


	private void update (TransactionVO tvo) {
        Txaction txaction = txactionDao.select(tvo.getTxId());
        Account firstAccount = accountDao.select(tvo.getFirstAccount());
        Account secondAccount = accountDao.select(tvo.getSecondAccount());
        switch (tvo.getType()) {
            case INCOME:
                BigDecimal incomeDiff = txaction.getDebetAmmount().subtract(tvo.getFirstAmmount());
                txaction.setDebetAmmount(tvo.getFirstAmmount());
                firstAccount.setTotal(firstAccount.getTotal().add(incomeDiff));
                accountDao.update(firstAccount); 
                secondAccount.setTotal(secondAccount.getTotal().subtract(incomeDiff));
                accountDao.update(secondAccount);
                break;
            case EXPENSE:
                BigDecimal expenseDiff = txaction.getCreditAmmount().subtract(tvo.getFirstAmmount());
                txaction.setCreditAmmount(tvo.getFirstAmmount());
                firstAccount.setTotal(firstAccount.getTotal().add(expenseDiff));
                accountDao.update(firstAccount);
                secondAccount.setTotal(secondAccount.getTotal().add(expenseDiff));
                accountDao.update(secondAccount);
                break;
            case TRANSFER:
                if (firstAccount.getCurrency().equals(secondAccount.getCurrency())) { // same currency - one ammount
                    tvo.setSecondAmmount(tvo.getFirstAmmount());
                }
                BigDecimal creditDiff = txaction.getCreditAmmount().subtract(tvo.getFirstAmmount());
                BigDecimal debetDiff = txaction.getCreditAmmount().subtract(tvo.getSecondAmmount());
                txaction.setCreditAmmount(tvo.getFirstAmmount());
                txaction.setDebetAmmount(tvo.getSecondAmmount());
                firstAccount.setTotal(firstAccount.getTotal().add(creditDiff));
                accountDao.update(firstAccount);
                secondAccount.setTotal(secondAccount.getTotal().add(debetDiff));
                accountDao.update(secondAccount);
                break;
        }
        txaction.setTxDate(tvo.getDate());
        txaction.setComment(tvo.getNote());
        txactionDao.update(txaction);
    }
    
    
    @Transactional
    @Override
	public void delete (Integer transactionId) {
    	TransactionVO tvo = this.getTransaction(transactionId);
        Txaction txaction = txactionDao.select(tvo.getTxId());
        Account firstAccount = accountDao.select(tvo.getFirstAccount());
        Account secondAccount = accountDao.select(tvo.getSecondAccount());
        switch (tvo.getType()) {
        	case INCOME:
                firstAccount.setTotal(firstAccount.getTotal().subtract(tvo.getFirstAmmount()));
                accountDao.update(firstAccount);
                secondAccount.setTotal(secondAccount.getTotal().subtract(tvo.getFirstAmmount()));
                accountDao.update(secondAccount);
        		break;
        	case EXPENSE:
                firstAccount.setTotal(firstAccount.getTotal().subtract(tvo.getFirstAmmount()));
                accountDao.update(firstAccount);
                secondAccount.setTotal(secondAccount.getTotal().add(tvo.getFirstAmmount()));
                accountDao.update(secondAccount);
        		break;
            case TRANSFER:
                BigDecimal firstTotal = firstAccount.getTotal().add(tvo.getFirstAmmount());
                firstAccount.setTotal(firstTotal);
                BigDecimal secondTotal = secondAccount.getTotal().subtract(tvo.getSecondAmmount());
                secondAccount.setTotal(secondTotal);
                accountDao.update(firstAccount);
                accountDao.update(secondAccount);
                break;
        }
        txactionDao.delete(txaction);
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
        if (model.getDebet().getKind() == AccountKind.INCOME) {
            result.setType(TransactionType.INCOME);
            result.setFirstAccount(model.getDebet().getId());
            result.setFirstAmmount(model.getDebetAmmount());
            result.setSecondAccount(model.getCredit().getId());
        } else if (model.getCredit().getKind() == AccountKind.EXPENSE) {
            result.setType(TransactionType.EXPENSE);
            result.setFirstAccount(model.getCredit().getId());
            result.setFirstAmmount(model.getCreditAmmount());
            result.setSecondAccount(model.getDebet().getId());
        } else if (model.getCredit().getKind() == AccountKind.ASSETS && model.getDebet().getKind() == AccountKind.ASSETS) {
            result.setType(TransactionType.TRANSFER);
            result.setFirstAccount(model.getCredit().getId());
            result.setFirstAmmount(model.getCreditAmmount());
            result.setSecondAccount(model.getDebet().getId());
            result.setSecondAmmount(model.getDebetAmmount());
        } else {
            RuntimeException re = new IllegalArgumentException("Cannot determine transaction type.");
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
