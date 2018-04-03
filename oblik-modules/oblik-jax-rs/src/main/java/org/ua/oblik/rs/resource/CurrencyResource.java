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
import org.ua.oblik.soap.client.Currency;
import org.ua.oblik.soap.client.RedirectException_Exception;
import org.ua.oblik.soap.client.RedirectService;

@Component
@Path("/budgets/{budgetId}/currencies")
public class CurrencyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyResource.class);

    @Inject
    private RedirectService redirectService;

    @Inject
    private CurrencyStateAssembler currencyStateAssembler;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CurrencyResourceState> getCurrencies(@PathParam("budgetId") UUID budgetId) {
        LOGGER.info("Listing currencies from budget {}.", budgetId);
        List<Currency> currencies = redirectService.listCurrencies(budgetId.toString()).getItem();
        return currencies.stream()
                .map(currencyStateAssembler::toState)
                .collect(Collectors.toList());
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CurrencyResourceState postCurrency(@PathParam("budgetId") UUID budgetId,
                                              CurrencyResourceState currency) throws RedirectException_Exception {
        Currency toSave = currencyStateAssembler.convert(currency);
        Currency saved = redirectService.saveCurrency(budgetId.toString(), toSave);
        return currencyStateAssembler.toState(saved);
    }

    @PATCH
    @Path(("/{currencyId}"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CurrencyResourceState putCurrency(@PathParam("budgetId") UUID budgetId,
                                             @PathParam("currencyId") Integer currencyId,
                                             CurrencyResourceState state) throws RedirectException_Exception {
        Currency toSave = currencyStateAssembler.convert(state);
        toSave.setCurrencyId(currencyId);
        Currency saved = redirectService.saveCurrency(budgetId.toString(), toSave);
        return currencyStateAssembler.toState(saved);
    }

    @DELETE
    @Path("/{currencyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCurrency(@PathParam("budgetId") UUID budgetId,
                                   @PathParam("currencyId") Integer currencyId) {
        redirectService.deleteCurrency(budgetId.toString(), currencyId);
        return Response.noContent().build();
    }
}
