package org.ua.oblik.service;

import java.util.Date;
import java.util.List;
import org.ua.oblik.service.beans.TransactionVO;

/**
 *
 * @author Anton Bakalets
 */
public interface TransactionService {

    //@Transactional
    void save(TransactionVO tvo);
    
    TransactionVO getTransaction(Integer transactionId);
    
    List<TransactionVO> getTransactions();
    
    List<TransactionVO> getTransactions(Date date);
}
