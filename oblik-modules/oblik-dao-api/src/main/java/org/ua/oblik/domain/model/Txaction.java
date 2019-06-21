package org.ua.oblik.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
public interface Txaction extends Identifiable<Integer>, Serializable {

    Date getTxDate();

    void setTxDate(Date txDate);

    Account getCredit();

    void setCredit(Account credit);

    Account getDebet();

    void setDebet(Account debet);

    BigDecimal getCreditAmount();

    void setCreditAmount(BigDecimal creditAmount);

    BigDecimal getDebetAmount();

    void setDebetAmount(BigDecimal debetAmount);

    String getComment();

    void setComment(String comment);
}
