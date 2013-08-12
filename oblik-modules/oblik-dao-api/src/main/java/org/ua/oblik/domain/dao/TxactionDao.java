package org.ua.oblik.domain.dao;

import java.util.Date;
import java.util.List;
import org.ua.oblik.domain.model.Txaction;

/**
 *
 * @author Anton Bakalets
 */
public interface TxactionDao extends DaoFacade<Integer, Txaction> {
    
    List<Txaction> selectByMonth(Date date);
    
    List<Txaction> selectByDateRange(Date start, Date end);
    
}
