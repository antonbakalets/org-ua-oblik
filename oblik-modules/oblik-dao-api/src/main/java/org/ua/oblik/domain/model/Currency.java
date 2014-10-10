package org.ua.oblik.domain.model;

import java.math.BigDecimal;
import java.util.List;

import org.ua.oblik.domain.beans.Identifiable;

/**
 *
 */
public interface Currency extends Identifiable<Integer> {

    String getSymbol();

    void setSymbol(String symbol);

    boolean getByDefault();

    void setByDefault(boolean byDefault);

    BigDecimal getRate();

    void setRate(BigDecimal rate);

    List<Account> getAccounts();

    void setAccounts(List<Account> accounts);
}
