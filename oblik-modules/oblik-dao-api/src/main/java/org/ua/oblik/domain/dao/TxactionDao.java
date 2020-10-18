package org.ua.oblik.domain.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ua.oblik.domain.model.Txaction;

/**
 *
 * @author Anton Bakalets
 */
@Repository
public interface TxactionDao extends JpaRepository<Txaction, Integer> {

    List<Txaction> findByTxDateBetween(Date start, Date end);
    
}
