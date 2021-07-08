package org.ua.oblik.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Account entity.
 */
@Entity
@Table(name = "account")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acco_id")
    private Integer accoId;

    @Basic(optional = false)
    @Column(name = "short_name")
    private String shortName;

    @Basic(optional = false)
    @Column(name = "kind")
    @Enumerated(EnumType.STRING)
    private AccountKind kind;

    @Basic(optional = false)
    @Column(name = "total")
    private BigDecimal total;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency", referencedColumnName = "curr_id")
    private Currency currency;
}
