package org.ua.oblik.service;

import java.util.Date;
import java.util.List;

import org.ua.oblik.service.beans.TransactionVO;

/**
 *
 * @author Anton Bakalets
 */
public interface TransactionService {

    void save(TransactionVO tvo) throws NotFoundException, BusinessConstraintException;

    void delete(Integer transactionId) throws NotFoundException;

    TransactionVO getTransaction(Integer transactionId);

    List<TransactionVO> getTransactions(Date date);
}