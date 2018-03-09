package org.ua.oblik.soap;

import javax.jws.WebService;

@WebService(endpointInterface = "org.ua.oblik.soap.BudgetService")
public class BudgetServiceImpl implements BudgetService {

    @Override
    public String budgetName(String id) {
        return "Budget " + id + " name.";
    }

    @Override
    public void bugLog(String message) {
        System.out.println("message = " + message);
    }
}
