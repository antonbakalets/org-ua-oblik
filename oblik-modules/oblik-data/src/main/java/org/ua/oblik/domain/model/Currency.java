package org.ua.oblik.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Currency entity.
 */
@Entity
@Table(name = "currency")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "curr_id")
    private Integer currId;

    @Basic(optional = false)
    @Column(name = "symbol")
    private String symbol;

    @Basic(optional = false)
    @Column(name = "by_default")
    private boolean byDefault;

    @Basic(optional = false)
    @Column(name = "rate")
    private BigDecimal rate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currency", fetch = FetchType.LAZY)
    private List<Account> accounts;
}
