package com.restdatabus.web.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTest {

    private static final String product = "product";
    private static final String invoice = "invoice";
    private static final String customer = "customer";

    @Autowired
    private MockMvc mvc;

    @Test
    @WithUserDetails("poz")
    public void getAllDefinitions() throws Exception {
        this.mvc
            .perform(
                get(Constants.DEFINITIONS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect ( jsonPath( "$", hasSize( 3 )))
                .andExpect(content().string(containsString(invoice)))
                .andExpect(content().string(containsString(product)))
                .andExpect(content().string(containsString(customer)));
    }

    @Test
    @WithUserDetails("poz")
    public void getProductDefinitions() throws Exception {

        this.mvc.perform(
                get(Constants.DEFINITIONS + "/" + product))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect ( jsonPath( "$.name", is(product)))
                .andExpect ( jsonPath( "$.fields", hasSize( 3 )));
    }

    @Test
    @WithUserDetails("poz")
    @DirtiesContext
    public void deleteProductDefinition() throws Exception {


        this.mvc.perform(
                delete(Constants.DEFINITIONS + "/" + product))
                .andDo(print())
                .andExpect(status().isNoContent());

        this.mvc.perform(
                get(Constants.DEFINITIONS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect ( jsonPath( "$", hasSize( 2 )))
                .andExpect(content().string(containsString(invoice)))
                .andExpect(content().string(containsString(customer)));
    }
}
