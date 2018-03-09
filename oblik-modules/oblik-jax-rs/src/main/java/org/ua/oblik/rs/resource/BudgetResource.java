package org.ua.oblik.rs.resource;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.ua.oblik.service.BudgetService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.BudgetVO;

@Component
@Path("/budgets")
public class BudgetResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetResource.class);

    @Inject
    private BudgetService budgetService;

    public BudgetResource() {
        LOGGER.info("Creating budget resource.");
    }

    @GET
    @Path("/{budgetId}")
    @Produces(MediaType.APPLICATION_JSON)
    public BudgetRS getBudget(@PathParam("budgetId") UUID id) throws NotFoundException {
        LOGGER.info("Find budget by {}.", id);
        BudgetVO budget = budgetService.getBudget();
        BudgetRS budgetRS = new BudgetRS();
        budgetRS.setName(budget.getName() + id);
        budgetRS.setTotal(budget.getTotal());
        return budgetRS;
    }
}
