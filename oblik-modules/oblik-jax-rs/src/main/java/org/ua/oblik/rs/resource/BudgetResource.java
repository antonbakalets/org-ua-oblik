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
import org.ua.oblik.soap.client.Budget;
import org.ua.oblik.soap.client.RedirectService;

@Component
@Path("/budgets")
public class BudgetResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetResource.class);

    @Inject
    private RedirectService redirectService;

    public BudgetResource() {
        LOGGER.info("Creating budget resource.");
    }

    @GET
    @Path("/{budgetId}")
    @Produces(MediaType.APPLICATION_JSON)
    public BudgetRS getBudget(@PathParam("budgetId") UUID id) { // TODO exception
        LOGGER.info("Find budget by {}.", id);

        Budget budget = redirectService.getBudget(id.toString());

        BudgetRS budgetRS = new BudgetRS();
        budgetRS.setName(budget.getName() + id);
        budgetRS.setTotal(budget.getTotal());
        return budgetRS;
    }
}
