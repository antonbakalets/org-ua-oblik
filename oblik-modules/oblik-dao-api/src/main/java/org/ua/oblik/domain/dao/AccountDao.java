package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.AccountKind;

import java.util.List;

public interface AccountDao extends JpaRepository<Account, Integer>, AccountRepositoryFragment {

    List<Account> findByKind(AccountKind kind);

}
