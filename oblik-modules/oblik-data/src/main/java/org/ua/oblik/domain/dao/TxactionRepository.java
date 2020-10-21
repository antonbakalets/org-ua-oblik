package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ua.oblik.domain.model.Txaction;

import java.util.List;

/**
 * Transactions repository.
 */
@Repository
public interface TxactionRepository extends JpaRepository<Txaction, Integer> {

    List<Txaction> findAllByTxDateLessThanEqualAndTxDateGreaterThanEqual();
}
