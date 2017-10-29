package com.restdatabus.business.api.impl;

import static com.restdatabus.business.api.impl.EntityImplHelper.*;

import com.restdatabus.model.data.Entity;
import com.restdatabus.model.data.dvo.EntityData;
import com.restdatabus.model.data.transform.EntityObjectMapper;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.service.EntityDefinitionService;
import com.restdatabus.model.service.EntityService;
import com.restdatabus.model.service.EntityValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InternalEntityManagerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(InternalEntityManagerImpl.class);

    @Autowired
    private EntityDefinitionService entityDefinitionService;

    @Autowired
    private EntityValidationService entityValidationService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private EntityObjectMapper entityObjectMapper;

    public EntityData create(EntityData entityData) {

        LOG.debug("create: {}", entityData);

        checkEntityType(LOG, entityData);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(entityData.getType());

        // Check type
        if(entityDefinition == null) {
            entityTypeNotExist(entityData.getType());
        }

        // Check fields
        List<String> errors = entityValidationService.verify(entityData.getData(), entityDefinition);
        if(errors.size() > 1) {
            LOG.error("error during validation of {} -> {}", entityData, errors);
        }

        // Persist
        Entity entity = entityObjectMapper.toEntityObject(entityData, entityDefinition);

        Entity persistedEntity = entityService.create(entityDefinition, entity);

        EntityData persistedData = entityObjectMapper.toDataObject(persistedEntity, entityDefinition);

        return persistedData;
    }

    public List<EntityData> findByType(String type) {

        LOG.debug("findByType: {}", type);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(type);

        // Check type
        if(entityDefinition == null) {
            entityTypeNotExist(type);
        }

        List<Entity> results = entityService.findByDefinition(entityDefinition);

        List<EntityData> data = new ArrayList<>();
        for(Entity entity: results) {
            data.add(
                   entityObjectMapper.toDataObject(entity, entityDefinition)
            );
        }

        return data;
    }

    private void entityTypeNotExist(String type) {

        String msg = "entity type '" + type + "' does not exist";
        LOG.error(msg);
        throw new IllegalArgumentException(msg);
    }
}
