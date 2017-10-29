package com.restdatabus.web.api;

import static com.restdatabus.web.api.Constants.*;

import com.restdatabus.business.api.EntityManager;
import com.restdatabus.business.api.impl.InternalEntityManagerImpl;
import com.restdatabus.model.data.dvo.EntityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntityController {

    private static final Logger LOG = LoggerFactory.getLogger(EntityController.class);

    private static final String FIELD_ID = "id";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_DATA = "data";

    @Autowired
    private InternalEntityManagerImpl imp;

    @Autowired
    private EntityManager entityManager;

    @RequestMapping(
            value = ENTITIES,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityData> create(@RequestBody EntityData entityData) {

        LOG.debug("create - {} -> {}", entityData.getType(), entityData.getData());

        EntityData persistedEntity =  entityManager.create(entityData);

        return new ResponseEntity<>(persistedEntity, HttpStatus.OK);
    }

    @RequestMapping(
            value = ENTITIES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<EntityData>> getByType(@RequestParam(value = "type") String type) {

        LOG.debug("getByType - {}", type);

        List<EntityData> results = entityManager.findByType(type);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @RequestMapping(
            value = ENTITY_BY_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityData> getByTypeAndId(@PathVariable(value = "id") Long id, @RequestParam(value = "type") String type) {

        LOG.debug("getByTypeAndId - {}", type);

        EntityData result = entityManager.findByTypeAndId(type, id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @RequestMapping(
            value = ENTITY_BY_ID,
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id") Long id, @RequestParam(value = "type") String type) {

        LOG.debug("deleteById: {}, id", type);

        entityManager.deleteByTypeAndId(type, id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
