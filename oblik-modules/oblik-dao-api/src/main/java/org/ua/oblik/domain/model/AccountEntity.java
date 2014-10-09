package org.ua.oblik.domain.model;

import java.math.BigDecimal;

import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.beans.Identifiable;

/**
 *
 */
public interface AccountEntity extends Identifiable<Integer> {

    String getShortName();

    void setShortName(String shortName);

    AccountKind getKind();

    void setKind(AccountKind kind);

    BigDecimal getTotal();

    void setTotal(BigDecimal total);

    CurrencyEntity getCurrency();

    void setCurrency(CurrencyEntity currency);
}
