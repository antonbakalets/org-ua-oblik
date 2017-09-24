package org.ua.oblik.service.beans;

public class TransactionFactory {

    public TransactionVO create(TransactionType type) {
        TransactionVO result = new TransactionVO();
        result.setType(type);
        return result;
    }
}
