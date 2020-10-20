package org.ua.oblik.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.AccountKind;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * Convert.
     */
    @Mapping(target = "accountId", source = "id")
    @Mapping(target = "currencyId", source = "currency.id")
    @Mapping(target = "currencySymbol", source = "currency.symbol")
    @Mapping(target = "name", source = "shortName")
    @Mapping(target = "amount", source = "total")
    @Mapping(target = "type", source = "kind")
    AccountVO convert(Account account);

    /**
     * Convert.
     */
    AccountVOType convert(AccountKind kind);

    /**
     * Convert.
     */
    AccountKind convert(AccountVOType type);
}
