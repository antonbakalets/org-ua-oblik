package org.ua.oblik.rest.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {org.ua.oblik.config.AppConfig.class})
public class BudgetControllerTest {

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BudgetController())
                .alwaysExpect(status().isOk())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/budget")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("{\"" + BudgetController.KEY.toString() + "\":\"Budget name\"}", contentAsString);
    }

    @Test
    public void testGet() throws Exception {
        UUID uuid = UUID.randomUUID();
        MvcResult mvcResult = mockMvc.perform(get("/budget/{id}", uuid.toString())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("BudgetName" + uuid, contentAsString);
    }
}
