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

    @Autowired
    private InternalEntityManagerImpl imp;

    @Autowired
    private EntityManager entityManager;

    @RequestMapping(
            value = ENTITIES_BY_TYPE,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityData> create(
            @PathVariable(value = "name") String type,
            @RequestBody EntityData entityData
    ) {

        LOG.debug("create - {} -> {}", type, entityData.getData());

        entityData.setType(type);

        EntityData persistedEntity =  entityManager.create(entityData);

        return new ResponseEntity<>(persistedEntity, HttpStatus.OK);
    }

    @RequestMapping(
            value = ENTITIES_BY_TYPE,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<EntityData>> getByType(@PathVariable(value = "name") String type) {

        LOG.debug("getByType - {}", type);

        List<EntityData> results = entityManager.findByType(type);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @RequestMapping(
            value = ENTITY_BY_TYPE_AND_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityData> getByTypeAndId(@PathVariable(value = "name") String type, @PathVariable(value = "id") Long id) {

        LOG.debug("getByTypeAndId - {}", type);

        EntityData result = entityManager.findByTypeAndId(type, id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(
            value = ENTITY_BY_TYPE_AND_ID,
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> deleteById(@PathVariable(value = "name") String type, @PathVariable(value = "id") Long id) {

        LOG.debug("deleteById: {}, id", type);

        entityManager.deleteByTypeAndId(type, id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = ENTITY_BY_TYPE_AND_ID,
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityData> update(@PathVariable(value = "name") String type, @PathVariable(value = "id") Long id, @RequestBody EntityData data) {

        LOG.debug("update: {}/{} -> {}", id, type, data);

        data.setType(type);

        EntityData updatedData = entityManager.update(type, id, data);

        return new ResponseEntity<>(updatedData, HttpStatus.OK);
    }
}
