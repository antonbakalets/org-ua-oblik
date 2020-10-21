package org.ua.oblik.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@SqlResultSetMapping(
        name = CurrencyTotal.NAME,
        entities = @EntityResult(
                entityClass = CurrencyTotal.class,
                fields = {
                        @FieldResult(name = "currId", column = "curr_id"),
                        @FieldResult(name = "symbol", column = "symbol"),
                        @FieldResult(name = "byDefault", column = "by_default"),
                        @FieldResult(name = "rate", column = "rate"),
                        @FieldResult(name = "total", column = "sumtotal")
                })
)
@NamedQuery(name = "assetsByCurrency",
        query = "SELECT c.curr_id, c.symbol, c.by_default, c.rate, coalesce(a.sumtotal, 0) as sumtotal "
                + "  FROM currency c "
                + "  LEFT JOIN (SELECT currency, sum(total) AS sumtotal FROM account WHERE account.kind='ASSETS' GROUP BY currency) AS a "
                + "    ON c.curr_id = a.currency")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CurrencyTotal {

    public static final String NAME = "currencyTotal";

    @Id
    private Integer currId;
    private String symbol;
    private boolean byDefault;
    private BigDecimal rate;
    private BigDecimal total;
}
