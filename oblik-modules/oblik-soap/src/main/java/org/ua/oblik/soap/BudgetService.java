package org.ua.oblik.soap;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface BudgetService {

    @WebMethod
    String budgetName(String id);

    @WebMethod
    @Oneway
    void bugLog(String message);
}
