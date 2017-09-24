package org.ua.oblik.rest.v1.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ua.oblik.rest.v1.dto.CurrencyResource;
import org.ua.oblik.service.beans.CurrencyVO;

@Component
public class CurrencyConverter implements Converter<CurrencyResource, CurrencyVO> {

    @Override
    public CurrencyVO convert(CurrencyResource resource) {
        CurrencyVO vo = new CurrencyVO();
        vo.setSymbol(resource.getSymbol());
        vo.setRate(resource.getRate());
        return vo;
    }
}
