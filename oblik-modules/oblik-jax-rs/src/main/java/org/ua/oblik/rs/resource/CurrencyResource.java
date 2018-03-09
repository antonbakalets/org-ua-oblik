package org.ua.oblik.rs.resource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.CurrencyVO;

@Component
@Path("/budgets/{budgetId}/currencies")
public class CurrencyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyResource.class);

    @Inject
    private CurrencyService currencyService;

    @Inject
    private CurrencyStateAssembler currencyStateAssembler;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CurrencyResourceState> getCurrencies(@PathParam("budgetId") UUID budgetId) {
        LOGGER.info("Listing currencies from budget {}.", budgetId);
        return currencyService.getCurrencies().stream()
                .map(currencyStateAssembler::toState)
                .collect(Collectors.toList());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CurrencyResourceState postCurrency(@PathParam("budgetId") UUID budgetId,
                                              CurrencyResourceState currency) throws NotFoundException, BusinessConstraintException {
        CurrencyVO saved = currencyStateAssembler.convert(currency);
        currencyService.save(saved);
        return currencyStateAssembler.toState(saved);
    }

    @PUT
    @Path(("/{currencyId}"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CurrencyResourceState putCurrency(@PathParam("budgetId") UUID budgetId,
                                             @PathParam("currencyId") Integer currencyId,
                                             CurrencyResourceState state) throws NotFoundException, BusinessConstraintException {
        CurrencyVO currencyVO = currencyStateAssembler.convert(state);
        currencyVO.setCurrencyId(currencyId);
        currencyService.save(currencyVO);
        return currencyStateAssembler.toState(currencyVO);
    }

    @DELETE
    @Path("/{currencyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCurrency(@PathParam("budgetId") UUID budgetId,
                                   @PathParam("currencyId") Integer currencyId) throws NotFoundException, BusinessConstraintException {
        currencyService.remove(currencyId);
        return Response.noContent().build();
    }
}
