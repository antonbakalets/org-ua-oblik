package org.ua.oblik.soap;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC) // versus Style.DOCUMENT, the default
public interface RedirectService {

    @WebMethod
    Budget getBudget(String id);

    @WebMethod
    Currency[] listCurrencies(String budgetId);

    @WebMethod
    Currency saveCurrency(String budgetid, Currency currency);

    @WebMethod
    @Oneway
    void deleteCurrency(String budgetId, Integer currencyId);

}
