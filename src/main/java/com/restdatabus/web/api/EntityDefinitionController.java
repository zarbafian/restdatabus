package com.restdatabus.web.api;

import static com.restdatabus.web.api.Constants.*;

import com.restdatabus.business.api.EntityDefinitionManager;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
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
            value = DEFINITIONS,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityDefinitionData> create(@RequestBody EntityDefinitionData data) {

        LOG.debug("create: {}", data);

        EntityDefinitionData persistedData = manager.create(data);

        return new ResponseEntity<>(persistedData, HttpStatus.OK);
    }

    @RequestMapping(
            value = FIELD_BY_NAME_AND_FIELD,
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> deleteFieldDefinition(@PathVariable(value = "name") String name, @PathVariable(value = "field") String field) {

        LOG.debug("deleteFieldDefinition: {} -> ", name, field);

        manager.deleteField(name, field);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = FIELD_BY_NAME_AND_FIELD,
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FieldDefinitionData> updateFieldDefinition(@PathVariable(value = "name") String name, @PathVariable(value = "field") String field, @RequestBody FieldDefinitionData data) {

        LOG.debug("updateFieldDefinition: {} -> ", name, field);

        FieldDefinitionData updatedField = manager.updateField(name, field, data);

        return new ResponseEntity<>(updatedField, HttpStatus.OK);
    }

    @RequestMapping(
            value = FIELDS_BY_NAME,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FieldDefinitionData> addFieldDefinition(@PathVariable(value = "name") String name, @RequestBody FieldDefinitionData data) {

        LOG.debug("addFieldDefinition: {}", data);

        FieldDefinitionData persistedData = manager.createField(name, data);

        return new ResponseEntity<>(persistedData, HttpStatus.OK);
    }

    @RequestMapping(
            value = DEFINITION_BY_NAME,
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityDefinitionData> update(@PathVariable(value = "name") String name, @RequestBody EntityDefinitionData data) {

        LOG.debug("update: {}, {}", name, data);

        EntityDefinitionData updatedData = manager.update(name, data);

        return new ResponseEntity<>(updatedData, HttpStatus.OK);
    }

    @RequestMapping(
            value = DEFINITION_BY_NAME,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityDefinitionData> getByName(@PathVariable(value = "name") String name) {

        LOG.debug("getByName: {}", name);

        EntityDefinitionData foundData = manager.findByName(name);

        if(foundData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundData, HttpStatus.OK);
    }

    @RequestMapping(
            value = DEFINITION_BY_NAME,
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> deleteByName(@PathVariable(value = "name") String name) {

        LOG.debug("deleteByName: {}", name);

        manager.deleteByName(name);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = DEFINITIONS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<EntityDefinitionData>> allDefinitions() {

        LOG.debug("allDefinitions");

        List<EntityDefinitionData> persistedData = manager.findAll();

        return new ResponseEntity<>(persistedData, HttpStatus.OK);
    }
}
