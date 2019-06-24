package org.ua.oblik.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public interface Currency extends Identifiable<Integer>, Serializable {

    String getSymbol();

    void setSymbol(String symbol);

    boolean isByDefault();

    void setByDefault(boolean byDefault);

    BigDecimal getRate();

    void setRate(BigDecimal rate);

    List<Account> getAccounts();

    void setAccounts(List<Account> accounts);
}
