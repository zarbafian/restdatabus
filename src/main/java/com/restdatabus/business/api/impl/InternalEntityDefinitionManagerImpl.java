package com.restdatabus.business.api.impl;

import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
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

/**
 * Internal manager layer: handle all business logic.
 */
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

        // Check if the name is not already exist
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

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public boolean deleteByName(String name) {

        LOG.debug("> delete: {}", name);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(name);

        if(entityDefinition == null) {

            LOG.debug("= delete: entity {} does not exist", name);

            return false;
        }

        fieldDefinitionService.deleteByEntityDefinition(entityDefinition.getId());

        entityDefinitionService.delete(entityDefinition.getId());

        LOG.debug("< delete: {}", name);

        return true;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public EntityDefinitionData update(EntityDefinitionData existingData, EntityDefinitionData newData) {

        LOG.debug("update: {} -> {}", existingData.getName(), newData);

        // Has the name changed
        if(! existingData.getName().equals(newData.getName()) ) {

            // Yes, check if the new name is not already exist
            EntityDefinition entityDefinition = entityDefinitionService.findByName(newData.getName());

            if (entityDefinition != null) {
                String msg = "the new name '" + newData.getName() + "' already exists";
                LOG.error(msg);
                throw new IllegalArgumentException(msg);
            }
        }

        // Get existing instance
        EntityDefinition existingDefinition = entityDefinitionService.findByName(existingData.getName());

        // Transform new data to a persistable form
        EntityDefinition newEntityDefinition = EntityDefinitionHelper.dvoToPersist(newData);

        // Transfer data
        existingDefinition.setName(newEntityDefinition.getName());

        // Update entity
        EntityDefinition updatedEntity = entityDefinitionService.update(existingDefinition);

        EntityDefinitionData updatedData = EntityDefinitionHelper.persistToDvo(updatedEntity);

        return updatedData;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public FieldDefinitionData createField(String name, FieldDefinitionData data) {

        LOG.debug("> createField: {} -> {}", name, data);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(name);

        if(entityDefinition == null) {

            String msg = "the entity '" + name + "' does not exists";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        FieldDefinition fieldDefinition = fieldDefinitionService.findByDefinitionAndName(entityDefinition.getId(), data.getName());

        if(fieldDefinition != null) {

            String msg = "the entity '" + name + "' already has a field '" + data.getName() + "'";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        fieldDefinition = EntityDefinitionHelper.dvoToPersist(data);
        fieldDefinition.setEntityDefinitionId(entityDefinition.getId());

        FieldDefinition persistedField = fieldDefinitionService.create(fieldDefinition);

        LOG.debug("< createField: {} -> {}", name, persistedField);

        FieldDefinitionData persistedData = EntityDefinitionHelper.persistToDvo(persistedField);

        return persistedData;
    }


    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public FieldDefinitionData updateField(String name, String field, FieldDefinitionData data) {

        LOG.debug("> updateField: {}.{} -> {}", name, field, data);

        // Check that entity exit
        EntityDefinition entityDefinition = entityDefinitionService.findByName(name);
        if(entityDefinition == null) {

            String msg = "the entity '" + name + "' does not exists";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Check that field exist
        FieldDefinition existingFieldDefinition = fieldDefinitionService.findByDefinitionAndName(entityDefinition.getId(), field);
        if(existingFieldDefinition == null) {

            String msg = "the entity '" + name + "' does not a field '" + field + "'";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Transform new data to persistable form
        FieldDefinition newData = EntityDefinitionHelper.dvoToPersist(data);

        // Has the name changed
        if(! existingFieldDefinition.getName().equals(newData.getName()) ) {

            // Yes, check if the new name is not already exist
            FieldDefinition fieldDefinition = fieldDefinitionService.findByDefinitionAndName(entityDefinition.getId(), newData.getName());

            if (fieldDefinition != null) {
                String msg = "the new field name '" + newData.getName() + "' already exists";
                LOG.error(msg);
                throw new IllegalArgumentException(msg);
            }
        } else {
            LOG.debug("new field name available: {}.{} -> {}.{}", name, field, name, newData.getName());
        }

        // Transfer new data to existing field definition
        existingFieldDefinition.setName(newData.getName());
        existingFieldDefinition.setType(newData.getType());

        // Persist changes
        FieldDefinition updatedField = fieldDefinitionService.update(existingFieldDefinition);

        LOG.debug("< updateField: {} -> {}", name, updatedField);

        FieldDefinitionData updatedData = EntityDefinitionHelper.persistToDvo(updatedField);

        return updatedData;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public boolean deleteField(String name, String field) {

        LOG.debug("> deleteField: {} -> {}", name, field);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(name);

        if(entityDefinition == null) {

            LOG.debug("= deleteField: entity {} does not exist", name);

            return false;
        }

        FieldDefinition fieldDefinition = fieldDefinitionService.findByDefinitionAndName(entityDefinition.getId(), field);

        if(fieldDefinition == null) {

            LOG.debug("= deleteField: field {} does not exist for entity {}", field, name);

            return false;
        }

        fieldDefinitionService.delete(fieldDefinition.getId());

        LOG.debug("< deleteField: {}", name);

        return true;
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

    public FieldDefinitionData findByNameAndDefinition(String name, String field) {

        LOG.debug("> findByNameAndDefinition: {}", name, field);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(name);

        if(entityDefinition == null) {

            LOG.debug("= findByNameAndDefinition: entity {} does not exist", name);

            return null;
        }

        FieldDefinition fieldDefinition = fieldDefinitionService.findByDefinitionAndName(entityDefinition.getId(), field);

        if(fieldDefinition == null) {

            LOG.debug("< findByNameAndDefinition: {} -> {} not found", name, field);

            return null;
        }

        LOG.debug("< findByNameAndDefinition: found {}", entityDefinition);

        FieldDefinitionData fieldDefinitionData = EntityDefinitionHelper.persistToDvo(fieldDefinition);

        return fieldDefinitionData;
    }
}
