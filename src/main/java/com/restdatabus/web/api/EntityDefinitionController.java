package com.restdatabus.web.api;

import com.restdatabus.business.api.EntityDefinitionManager;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntityDefinitionController {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionController.class);

    @Autowired
    private EntityDefinitionManager manager;

    @RequestMapping(
            value = "/definitions",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EntityDefinitionData create(@RequestBody EntityDefinitionData data) {

        LOG.debug("create: {}", data);

        EntityDefinitionData persistedData = manager.create(data);

        return persistedData;
    }

    @RequestMapping(
            value = "/definitions/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityDefinitionData> definitionByName(@PathVariable(value = "name") String name) {

        LOG.debug("definitionByName: {}", name);

        EntityDefinitionData foundData = manager.findByName(name);

        if(foundData == null) {
            return new ResponseEntity<EntityDefinitionData>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<EntityDefinitionData>(foundData, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/definitions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<EntityDefinitionData>> allDefinitions() {

        LOG.debug("allDefinitions");

        List<EntityDefinitionData> persistedData = manager.findAll();

        return new ResponseEntity<List<EntityDefinitionData>>(persistedData, HttpStatus.OK);
    }
}
