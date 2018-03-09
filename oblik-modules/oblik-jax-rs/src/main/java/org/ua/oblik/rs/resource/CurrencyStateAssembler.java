package org.ua.oblik.rs.resource;

import org.springframework.stereotype.Service;
import org.ua.oblik.service.beans.CurrencyVO;

@Service
public class CurrencyStateAssembler {

    public CurrencyResourceState toState(CurrencyVO currencyVO) {
        CurrencyResourceState currencyResourceState = new CurrencyResourceState();
        currencyResourceState.setCurrencyId(currencyVO.getCurrencyId());
        currencyResourceState.setDefaultRate(currencyVO.getDefaultRate());
        currencyResourceState.setRate(currencyVO.getRate());
        currencyResourceState.setSymbol(currencyVO.getSymbol());
        currencyResourceState.setTotal(currencyVO.getTotal());
        return currencyResourceState;

    }

    public CurrencyVO convert(CurrencyResourceState currency) {
        CurrencyVO currencyVO = new CurrencyVO();
        currencyVO.setSymbol(currency.getSymbol());
        currencyVO.setRate(currency.getRate());
        return currencyVO;
    }
}
