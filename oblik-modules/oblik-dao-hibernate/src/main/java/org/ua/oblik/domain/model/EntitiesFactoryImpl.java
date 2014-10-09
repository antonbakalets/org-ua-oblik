package org.ua.oblik.domain.model;

/**
 *
 */
public class EntitiesFactoryImpl implements EntitiesFactory {

    @Override
    public AccountEntity createAccountEntity() {
        return new Account();
    }

    @Override
    public CurrencyEntity createCurrencyEntity() {
        return new Currency();
    }

    @Override
    public TxactionEntity createTxactionEntity() {
        return new Txaction();
    }

    @Override
    public UserLoginEntity createUserLoginEntity() {
        return new UserLogin();
    }
}
