package org.ua.oblik.domain.dao;

import java.util.Date;
import java.util.List;

import org.ua.oblik.domain.model.TxactionEntity;

/**
 *
 * @author Anton Bakalets
 */
public interface TxactionDao extends DaoFacade<Integer, TxactionEntity> {
    
    List<? extends TxactionEntity> selectByMonth(Date date);
    
    List<? extends TxactionEntity> selectByDateRange(Date start, Date end);
    
}
