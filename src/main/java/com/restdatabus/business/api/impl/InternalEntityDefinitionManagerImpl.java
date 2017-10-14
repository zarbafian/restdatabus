package com.restdatabus.business.api.impl;

import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.transform.EntityDefinitionHelper;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import com.restdatabus.model.service.EntityDefinitionService;
import com.restdatabus.model.service.FieldDefinitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true
)
@Service
public class InternalEntityDefinitionManagerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(InternalEntityDefinitionManagerImpl.class);

    @Autowired
    private EntityDefinitionService entityDefinitionService;

    @Autowired
    private FieldDefinitionService fieldDefinitionService;

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public EntityDefinitionData create(EntityDefinitionData data) {

        LOG.debug("create: {}", data);

        // Check if the name not already exist
        EntityDefinition entityDefinition = entityDefinitionService.findByName(data.getName());

        if (entityDefinition != null) {
            String msg = "the name '" + data.getName() + "' already exists";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Transform to a persistable form
        entityDefinition = EntityDefinitionHelper.dvoToPersist(data);

        // Persist entity
        EntityDefinition persistedEntity = entityDefinitionService.create(entityDefinition);

        // Persist fields
        for(FieldDefinition field: entityDefinition.getDefinitions()) {

            field.setEntityDefinitionId(persistedEntity.getId());
            FieldDefinition persistedField = fieldDefinitionService.create(field);
            persistedEntity.getDefinitions().add(persistedField);
        }

        EntityDefinitionData persistedData = EntityDefinitionHelper.persistToDvo(persistedEntity);

        return persistedData;
    }

    public EntityDefinitionData findByName(String name) {

        LOG.debug("> findByName: {}", name);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(name);

        if(entityDefinition == null) {

            LOG.debug("< findByName: {} not found", name);
            return null;
        }

        LOG.debug("< findByName: found {}", entityDefinition);
        EntityDefinitionData entityDefinitionData = EntityDefinitionHelper.persistToDvo(entityDefinition);

        return entityDefinitionData;
    }

    public List<EntityDefinitionData> findAll() {

        LOG.debug("> findAll");

        List<EntityDefinition> entityDefinitions = entityDefinitionService.findAll();

        List<EntityDefinitionData> results = new ArrayList<EntityDefinitionData>();

        LOG.debug("= findAll: found {} results", entityDefinitions.size());

        if(entityDefinitions.size() == 0) {

            return results;
        }

        for(EntityDefinition entityDefinition: entityDefinitions) {
            results.add(
                    EntityDefinitionHelper.persistToDvo(entityDefinition)
            );
        }

        return results;
    }
}
