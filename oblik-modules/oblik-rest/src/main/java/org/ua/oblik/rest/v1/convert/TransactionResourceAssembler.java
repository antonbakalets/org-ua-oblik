package org.ua.oblik.rest.v1.convert;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.ua.oblik.rest.v1.TransactionController;
import org.ua.oblik.rest.v1.dto.TransactionResource;
import org.ua.oblik.service.beans.TransactionVO;

@Component
public class TransactionResourceAssembler extends ResourceAssemblerSupport<TransactionVO, TransactionResource> {

    public TransactionResourceAssembler() {
        super(TransactionController.class, TransactionResource.class);
    }

    @Override
    protected TransactionResource instantiateResource(TransactionVO entity) {
        TransactionResource resource = new TransactionResource();
        resource.setType(entity.getType().toString());
        resource.setDate(LocalDateTime.ofInstant(entity.getDate().toInstant(), ZoneId.systemDefault()));
        resource.setFirstAccount(entity.getFirstAccount());
        resource.setFirstAmount(entity.getFirstAmount());
        resource.setSecondAccount(entity.getSecondAccount());
        resource.setSecondAmount(entity.getSecondAmount());
        resource.setNote(entity.getNote());
        return resource;
    }

    @Override
    public TransactionResource toResource(TransactionVO entity) {
        return createResourceWithId(entity.getTxId(), entity, entity.getBudgetId());
    }
}
