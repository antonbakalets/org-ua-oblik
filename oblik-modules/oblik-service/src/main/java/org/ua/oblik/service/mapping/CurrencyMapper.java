package org.ua.oblik.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.service.beans.CurrencyVO;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    @Mapping(target = "currencyId", source = "id")
    @Mapping(target = "defaultRate", source = "byDefault")
    CurrencyVO toVO(Currency currency);
}
