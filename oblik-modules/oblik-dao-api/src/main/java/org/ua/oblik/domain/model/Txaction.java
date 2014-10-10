package org.ua.oblik.domain.model;

import java.math.BigDecimal;
import java.util.Date;

import org.ua.oblik.domain.beans.Identifiable;

/**
 *
 */
public interface Txaction extends Identifiable<Integer> {

    Date getTxDate();

    void setTxDate(Date txDate);

    Account getCredit();

    void setCredit(Account credit);

    Account getDebet();

    void setDebet(Account debet);

    BigDecimal getCreditAmmount();

    void setCreditAmmount(BigDecimal creditAmmount);

    BigDecimal getDebetAmmount();

    void setDebetAmmount(BigDecimal debetAmmount);

    String getComment();

    void setComment(String comment);
}
