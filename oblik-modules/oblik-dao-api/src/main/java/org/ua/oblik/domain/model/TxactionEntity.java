package org.ua.oblik.domain.model;

import java.math.BigDecimal;
import java.util.Date;

import org.ua.oblik.domain.beans.Identifiable;

/**
 *
 */
public interface TxactionEntity extends Identifiable<Integer> {

    Date getTxDate();

    void setTxDate(Date txDate);

    AccountEntity getCredit();

    void setCredit(AccountEntity credit);

    AccountEntity getDebet();

    void setDebet(AccountEntity debet);

    BigDecimal getCreditAmmount();

    void setCreditAmmount(BigDecimal creditAmmount);

    BigDecimal getDebetAmmount();

    void setDebetAmmount(BigDecimal debetAmmount);

    String getComment();

    void setComment(String comment);
}
