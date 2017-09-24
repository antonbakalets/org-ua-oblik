package org.ua.oblik.service;

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
import org.ua.oblik.service.command.TransactionCommandFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private TransactionCommandFactory transactionCommandFactory;

    @Transactional
    @Override
    public void save(TransactionVO tvo) {
        LOG.debug("Saving {} transaction.", tvo.getType());
        transactionCommandFactory.createSaveCommand(tvo).execute();
    }

    @Override
    @Transactional
    public void delete(Integer transactionId) throws NotFoundException {
        LOG.debug("Deleting transaction by id: {}.", transactionId);
        if (txactionDao.exists(transactionId)) {
            TransactionVO tvo = new TransactionVO();
            tvo.setTxId(transactionId);
            transactionCommandFactory.createDeleteCommand(tvo).execute();
        } else {
            throw new NotFoundException("Could not find transaction.");
        }
    }

    @Override
    public TransactionVO getTransaction(Integer transactionId) {
        return convert(txactionDao.select(transactionId));
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

    public void setTransactionCommandFactory(TransactionCommandFactory transactionCommandFactory) {
        this.transactionCommandFactory = transactionCommandFactory;
    }
}
