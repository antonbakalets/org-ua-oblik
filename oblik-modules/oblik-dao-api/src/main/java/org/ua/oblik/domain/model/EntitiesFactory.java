package org.ua.oblik.domain.model;

/**
 *
 */
public interface EntitiesFactory {

    Account createAccountEntity();

    Currency createCurrencyEntity();

    Txaction createTxactionEntity();

    UserLogin createUserLoginEntity();
}
