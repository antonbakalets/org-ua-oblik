package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.AccountKind;

import java.util.List;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer>, AccountRepositoryFragment {

    List<Account> findByKind(AccountKind kind);

    boolean existsByShortName(String shortName);

}
