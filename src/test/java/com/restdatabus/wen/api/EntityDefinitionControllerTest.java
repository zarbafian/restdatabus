package com.restdatabus.wen.api;

import static com.restdatabus.web.api.Constants.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import com.restdatabus.web.api.EntityDefinitionController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EntityDefinitionController.class)
public class EntityDefinitionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EntityDefinitionController controller;

    @Test
    public void test() throws Exception {

        EntityDefinitionData data = new EntityDefinitionData();
        data.setName("bob");

        List<EntityDefinitionData> allDefinitions = Arrays.asList(
                new EntityDefinitionData("customer"),
                new EntityDefinitionData("invoice"),
                new EntityDefinitionData("product")
        );

        given( controller.allDefinitions() ).willReturn(
                new ResponseEntity<List<EntityDefinitionData>>(allDefinitions, HttpStatus.OK)
        );

        mvc.perform( get( DEFINITIONS))
                .andExpect( status().isOk() )
                .andExpect ( jsonPath( "$", hasSize(3)));
    }

    @Test
    public void testProduct() throws Exception {

        String product = "product";

        EntityDefinitionData data = new EntityDefinitionData();
        data.setName(product);

        data.setFields(
                Arrays.asList(
                        new FieldDefinitionData("reference", DataType.TEXT.getKey()),
                        new FieldDefinitionData("price", DataType.DECIMAL.getKey()),
                        new FieldDefinitionData("quantity", DataType.INTEGER.getKey())
                )
        );

        given( controller.getByName(product) ).willReturn(
                new ResponseEntity<EntityDefinitionData>(data, HttpStatus.OK)
        );

        mvc.perform( get( DEFINITIONS + "/" + product))
                .andExpect( status().isOk() )
                .andExpect ( jsonPath( "$.name", is(product )))
                .andExpect ( jsonPath( "$.fields", hasSize( 3 )));
    }
}
