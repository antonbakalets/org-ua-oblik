package org.ua.oblik.rest.v1.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ua.oblik.rest.v1.dto.AccountResource;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;

@Component
public class AccountConverter implements Converter<AccountResource, AccountVO> {

    @Override
    public AccountVO convert(AccountResource resource) {
        AccountVO vo = new AccountVO();
        vo.setCurrencyId(resource.getCurrencyId());
        vo.setName(resource.getName());
        vo.setType(AccountVOType.valueOf(resource.getType()));
        vo.setCurrencyId(resource.getCurrencyId());
        return vo;
    }
}
