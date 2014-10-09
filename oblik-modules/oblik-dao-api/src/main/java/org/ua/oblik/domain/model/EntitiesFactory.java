package org.ua.oblik.domain.model;

/**
 *
 */
public interface EntitiesFactory {

    AccountEntity createAccountEntity();

    CurrencyEntity createCurrencyEntity();

    TxactionEntity createTxactionEntity();

    UserLoginEntity createUserLoginEntity();
}
