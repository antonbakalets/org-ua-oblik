package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ua.oblik.domain.model.Txaction;

import java.util.Date;
import java.util.List;

/**
 * Transactions repository.
 */
@Repository
public interface TxactionRepository extends JpaRepository<Txaction, Integer> {

    /**
     * Find all transactions between end and start date.
     */
    List<Txaction> findAllByTxDateLessThanEqualAndTxDateGreaterThanEqual(Date start, Date end);
}
