package org.ua.oblik.service;

import org.ua.oblik.service.beans.TransactionVO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Anton Bakalets
 */
public interface TransactionService {

    void save(TransactionVO tvo);

    void delete(Integer transactionId) throws NotFoundException;

    TransactionVO getTransaction(Integer transactionId);

    List<TransactionVO> getTransactions(Date date);
}