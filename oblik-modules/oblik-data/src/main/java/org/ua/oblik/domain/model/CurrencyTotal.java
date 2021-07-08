package org.ua.oblik.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@SqlResultSetMapping(
        name = CurrencyTotal.MAPPING_NAME,
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
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CurrencyTotal {

    public static final String MAPPING_NAME = "CurrencyTotal.assetsByCurrency";

    @Id
    private Integer currId;
    private String symbol;
    private boolean byDefault;
    private BigDecimal rate;
    private BigDecimal total;
}
