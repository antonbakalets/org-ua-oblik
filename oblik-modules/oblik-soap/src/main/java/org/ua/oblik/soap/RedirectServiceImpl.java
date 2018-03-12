package org.ua.oblik.soap;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.jackson.JacksonFeature;

@WebService(endpointInterface = "org.ua.oblik.soap.RedirectService")
public class RedirectServiceImpl implements RedirectService {

    private static final String REST_URI
            = "http://localhost:8090/oblik-api/v1/budgets";
    public static final String CURRENCIES1 = "currencies";
    public static final String CURRENCIES = CURRENCIES1;

    private Client client;

    public RedirectServiceImpl() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
        clientConfig.register(JacksonFeature.class);
        client = ClientBuilder.newClient(clientConfig);
    }

    @Override
    public Budget getBudget(String id) {
        return client
                .target(REST_URI)
                .path(id)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Budget.class);
    }

    @Override
    public Currency[] listCurrencies(String budgetId) { // TODO UUID
        List<Currency> currencies = client
                .target(REST_URI)
                .path(budgetId)
                .path(CURRENCIES)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Currency>>() {
                });
        return currencies.toArray(new Currency[0]);
    }

    @Override
    public Currency saveCurrency(String budgetId, Currency currency) {
        if (currency.getCurrencyId() == null) {
            return client
                    .target(REST_URI)
                    .path(budgetId)
                    .path(CURRENCIES1)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(currency, MediaType.APPLICATION_JSON_TYPE), Currency.class);
        } else {
            return client
                    .target(REST_URI)
                    .path(budgetId)
                    .path(CURRENCIES1)
                    .path(currency.getCurrencyId().toString())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .method("PATCH", Entity.entity(currency, MediaType.APPLICATION_JSON_TYPE), Currency.class);
        }
    }

    @Override
    public void deleteCurrency(String budgetId, Integer currencyId) {
        client.target(REST_URI)
                .path(budgetId)
                .path(CURRENCIES1)
                .path(currencyId.toString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();
    }
}
