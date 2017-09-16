package org.ua.oblik.rest.v1.convert;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.ua.oblik.rest.v1.CurrencyController;
import org.ua.oblik.rest.v1.dto.CurrencyResource;
import org.ua.oblik.service.beans.CurrencyVO;

@Component
public class CurrencyResourceAssembler extends ResourceAssemblerSupport<CurrencyVO, CurrencyResource> {

    public CurrencyResourceAssembler() {
        super(CurrencyController.class, CurrencyResource.class);
    }

    @Override
    protected CurrencyResource instantiateResource(CurrencyVO entity) {
        CurrencyResource dto = new CurrencyResource();
        dto.setCurrencyId(entity.getCurrencyId());
        dto.setDefaultRate(entity.getDefaultRate());
        dto.setSymbol(entity.getSymbol());
        dto.setRate(entity.getRate());
        dto.setTotal(entity.getTotal());
        return dto;
    }

    @Override
    public CurrencyResource toResource(CurrencyVO entity) {
        return createResourceWithId(entity.getCurrencyId(), entity, entity.getBudgetId());
    }
}
