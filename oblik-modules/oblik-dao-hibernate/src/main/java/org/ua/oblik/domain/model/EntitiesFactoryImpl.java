package org.ua.oblik.domain.model;

/**
 *
 */
public class EntitiesFactoryImpl implements EntitiesFactory {

    @Override
    public Account createAccountEntity() {
        return new AccountEntity();
    }

    @Override
    public Currency createCurrencyEntity() {
        return new CurrencyEntity();
    }

    @Override
    public Txaction createTxactionEntity() {
        return new TxactionEntity();
    }

    @Override
    public UserLogin createUserLoginEntity() {
        return new UserLoginEntity();
    }
}
