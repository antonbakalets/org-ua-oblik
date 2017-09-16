package org.ua.oblik.rest.v1.convert;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.ua.oblik.rest.v1.AccountController;
import org.ua.oblik.rest.v1.dto.AccountResource;
import org.ua.oblik.service.beans.AccountVO;

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<AccountVO, AccountResource> {

    public AccountResourceAssembler() {
        super(AccountController.class, AccountResource.class);
    }

    @Override
    protected AccountResource instantiateResource(AccountVO entity) {
        AccountResource resource = new AccountResource();
        resource.setName(entity.getName());
        resource.setType(entity.getType().toString());
        resource.setSymbol(entity.getCurrencySymbol());
        resource.setAmount(entity.getAmount());
        return resource;
    }

    @Override
    public AccountResource toResource(AccountVO entity) {
        return createResourceWithId(entity.getAccountId(), entity, entity.getBudgetId());
    }
}
