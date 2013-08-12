package org.ua.oblik.service.beans;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionFactory {

    public TransactionVO create(String type) {
        return create(TransactionType.valueOf(type.toUpperCase()));
    }

    public TransactionVO create(TransactionType type) {
        TransactionVO result = new TransactionVO();
        result.setType(type);
        return result;
    }
}
