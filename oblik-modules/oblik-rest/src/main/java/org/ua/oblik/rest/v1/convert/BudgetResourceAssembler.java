package org.ua.oblik.rest.v1.convert;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.UUID;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.ua.oblik.rest.v1.AccountController;
import org.ua.oblik.rest.v1.BudgetController;
import org.ua.oblik.rest.v1.CurrencyController;
import org.ua.oblik.rest.v1.TransactionController;
import org.ua.oblik.rest.v1.dto.BudgetResource;
import org.ua.oblik.service.beans.BudgetVO;

@Component
public class BudgetResourceAssembler extends ResourceAssemblerSupport<BudgetVO, BudgetResource> {

    public BudgetResourceAssembler() {
        super(BudgetController.class, BudgetResource.class);
    }

    @Override
    protected BudgetResource instantiateResource(BudgetVO entity) {
        BudgetResource resource = new BudgetResource();
        resource.setName(entity.getName());
        resource.setTotal(entity.getTotal());
        return resource;
    }

    @Override
    public BudgetResource toResource(BudgetVO entity) {
        UUID id = entity.getBudgetId();
        BudgetResource resource = createResourceWithId(id, entity);
        resource.add(linkTo(CurrencyController.class, id).withRel("currencies"));
        resource.add(linkTo(AccountController.class, id).withRel("assets"));
        resource.add(linkTo(AccountController.class, id).withRel("expenses"));
        resource.add(linkTo(AccountController.class, id).withRel("incomes"));
        resource.add(linkTo(TransactionController.class, id).withRel("transactions"));
        return resource;
    }
}
