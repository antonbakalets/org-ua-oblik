package org.ua.oblik.rest.v1.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ua.oblik.rest.v1.dto.TransactionResource;
import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;

@Component
public class TransactionConverter implements Converter<TransactionResource, TransactionVO> {

    @Override
    public TransactionVO convert(TransactionResource resource) {
        TransactionVO vo = new TransactionVO();
        vo.setType(TransactionType.valueOf(resource.getType()));
        vo.setDate(resource.getDate());
        vo.setFirstAccount(resource.getFirstAccount());
        vo.setFirstAmount(resource.getFirstAmount());
        vo.setSecondAccount(resource.getSecondAccount());
        vo.setSecondAmount(resource.getSecondAmount());
        vo.setNote(resource.getNote());
        return vo;
    }
}
