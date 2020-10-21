package org.ua.oblik.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Transaction entity.
 */
@Entity
@Table(name = "txaction")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Txaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "txac_id")
    private Integer txacId;

    @Basic(optional = false)
    @Column(name = "tx_date")
    @Temporal(TemporalType.DATE)
    private Date txDate;

    @ManyToOne
    @JoinColumn(name = "credit", referencedColumnName = "acco_id")
    private Account credit;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    @ManyToOne
    @JoinColumn(name = "debet", referencedColumnName = "acco_id")
    private Account debet;

    @Column(name = "debet_amount")
    private BigDecimal debetAmount;

    @Column(name = "tx_comment")
    private String comment;
}
