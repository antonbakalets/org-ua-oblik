package org.ua.oblik.rs.resource;

import org.springframework.stereotype.Service;
import org.ua.oblik.soap.client.Currency;
import org.ua.oblik.soap.client.ObjectFactory;

@Service
public class CurrencyStateAssembler {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    public CurrencyResourceState toState(Currency currency) {
        CurrencyResourceState currencyResourceState = new CurrencyResourceState();
        currencyResourceState.setCurrencyId(currency.getCurrencyId());
        currencyResourceState.setDefaultRate(currency.isDefaultRate());
        currencyResourceState.setRate(currency.getRate());
        currencyResourceState.setSymbol(currency.getSymbol());
        currencyResourceState.setTotal(currency.getTotal());
        return currencyResourceState;

    }

    public Currency convert(CurrencyResourceState state) {
        Currency currency = OBJECT_FACTORY.createCurrency();
        currency.setSymbol(state.getSymbol());
        currency.setRate(state.getRate());
        return currency;
    }
}
