package org.ua.oblik.domain.model;

import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.beans.Identifiable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
public interface Account extends Identifiable<Integer>, Serializable {

    String getShortName();

    void setShortName(String shortName);

    AccountKind getKind();

    void setKind(AccountKind kind);

    BigDecimal getTotal();

    void setTotal(BigDecimal total);

    Currency getCurrency();

    void setCurrency(Currency currency);
}
