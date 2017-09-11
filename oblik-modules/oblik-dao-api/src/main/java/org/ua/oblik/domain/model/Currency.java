package org.ua.oblik.domain.model;

import org.ua.oblik.domain.beans.Identifiable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public interface Currency extends Identifiable<Integer>, Serializable {

    String getSymbol();

    void setSymbol(String symbol);

    boolean getByDefault();

    void setByDefault(boolean byDefault);

    BigDecimal getRate();

    void setRate(BigDecimal rate);

    List<Account> getAccounts();

    void setAccounts(List<Account> accounts);
}
