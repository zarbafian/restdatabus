package com.restdatabus.business.api.impl;

import static com.restdatabus.business.api.impl.EntityDefinitionImplHelper.*;

import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import com.restdatabus.model.data.dvo.FieldTypeData;
import com.restdatabus.model.data.transform.EntityDefinitionObjectMapper;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import com.restdatabus.model.meta.FieldType;
import com.restdatabus.model.service.*;
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
    private InternalFieldDefinitionManagerImpl fieldImpl;

    @Autowired
    private EntityDefinitionService entityDefinitionService;

    @Autowired
    private FieldDefinitionService fieldDefinitionService;

    @Autowired
    private FieldTypeService fieldTypeService;

    @Autowired
    private EntityTableService entityTableService;

    @Autowired
    private RelationDefinitionService relationDefinitionService;

    @Autowired
    EntityDefinitionObjectMapper entityDefinitionObjectMapper;

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public EntityDefinitionData create(EntityDefinitionData data) {

        LOG.debug("create: {}", data);

        checkEntityName(data);

        // Check if the name is not already exist
        EntityDefinition entityDefinition = entityDefinitionService.findByName(data.getName());

        if (entityDefinition != null) {
            String msg = entityAlreadyExist(data.getName());
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Transform to a persistable form
        entityDefinition = entityDefinitionObjectMapper.toEntityObject(data);

        // Persist entity
        EntityDefinition persistedEntity = entityDefinitionService.create(entityDefinition);

        // Create table
        entityTableService.createTable(persistedEntity);

        // Persist fields
        for(FieldDefinition field: entityDefinition.getDefinitions()) {

            field.setEntityDefinitionId(persistedEntity.getId());

            FieldDefinition persistedField = fieldImpl.create(field);

            persistedEntity.getDefinitions().add(persistedField);
        }

        return entityDefinitionObjectMapper.toDataObject(persistedEntity);
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

        // Delete fields
        for(FieldDefinition fieldDefinition: entityDefinition.getDefinitions()) {

            fieldImpl.delete(fieldDefinition);
        }

        // Delete entity definition
        entityDefinitionService.delete(entityDefinition.getId());

        // Delete table
        entityTableService.deleteTable(entityDefinition);

        LOG.debug("< delete: {}", name);

        return true;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public EntityDefinitionData update(EntityDefinitionData existingData, EntityDefinitionData newData) {

        LOG.debug("update: {} -> {}", existingData.getName(), newData);

        checkEntityName(newData);

        // Has the name changed
        if(! existingData.getName().equals(newData.getName()) ) {

            // Yes, check if the new name is not already exist
            EntityDefinition entityDefinition = entityDefinitionService.findByName(newData.getName());

            if (entityDefinition != null) {
                String msg = entityAlreadyExist(newData.getName());
                LOG.error(msg);
                throw new IllegalArgumentException(msg);
            }
        }

        // Get existing instance
        EntityDefinition existingDefinition = entityDefinitionService.findByName(existingData.getName());

        // Transform new data to a persistable form
        EntityDefinition newEntityDefinition = entityDefinitionObjectMapper.toEntityObject(newData);

        // Transfer data
        existingDefinition.setName(newEntityDefinition.getName());

        // Update entity
        EntityDefinition updatedEntity = entityDefinitionService.update(existingDefinition);

        return entityDefinitionObjectMapper.toDataObject(updatedEntity);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public FieldDefinitionData createField(String name, FieldDefinitionData data) {

        LOG.debug("> createField: {} -> {}", name, data);

        checkFieldName(data);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(name);

        if(entityDefinition == null) {

            String msg = entityDoesNotExist(name);
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        FieldDefinition fieldDefinition = fieldDefinitionService.findByDefinitionAndName(entityDefinition.getId(), data.getName());

        if(fieldDefinition != null) {

            String msg = fieldAlreadyExist(name, data.getName());
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        fieldDefinition = entityDefinitionObjectMapper.toEntityObject(data);
        fieldDefinition.setEntityDefinitionId(entityDefinition.getId());

        FieldDefinition persistedField = fieldImpl.create(fieldDefinition);

        LOG.debug("< createField: {} -> {}", name, persistedField);

        return entityDefinitionObjectMapper.toDataObject(persistedField);
    }


    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public FieldDefinitionData updateField(String name, String field, FieldDefinitionData data) {

        LOG.debug("> updateField: {}.{} -> {}", name, field, data);

        checkFieldName(data);

        // Check that entity exit
        EntityDefinition entityDefinition = entityDefinitionService.findByName(name);
        if(entityDefinition == null) {

            String msg = entityDoesNotExist(name);
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Check that field exist
        FieldDefinition existingFieldDefinition = fieldDefinitionService.findByDefinitionAndName(entityDefinition.getId(), field);

        if(existingFieldDefinition == null) {

            String msg = fieldDoesNotExist(name, field);
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Transform new data to persistable form (load field type id)
        FieldDefinition newData = entityDefinitionObjectMapper.toEntityObject(data);

        // Has the name changed
        if(! existingFieldDefinition.getName().equals(newData.getName()) ) {

            // Yes, check if the new name is not already exist
            FieldDefinition fieldDefinition = fieldDefinitionService.findByDefinitionAndName(entityDefinition.getId(), newData.getName());

            if (fieldDefinition != null) {
                String msg = fieldAlreadyExist(name, newData.getName());
                LOG.error(msg);
                throw new IllegalArgumentException(msg);
            }
            else {
                LOG.debug("new field name available: {}.{} -> {}.{}", name, field, name, newData.getName());
            }
        }

        // Transfer new data to existing field definition
        existingFieldDefinition.setName(newData.getName());
        existingFieldDefinition.setFieldTypeId(newData.getFieldTypeId());
        existingFieldDefinition.setTargetEntityId(newData.getTargetEntityId());

        // Persist changes
        FieldDefinition updatedField = fieldImpl.update(existingFieldDefinition);

        LOG.debug("< updateField: {} -> {}", name, updatedField);

        return entityDefinitionObjectMapper.toDataObject(updatedField);
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

        // Delete field definition
        fieldImpl.delete(fieldDefinition);

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

        return entityDefinitionObjectMapper.toDataObject(entityDefinition);
    }

    public List<EntityDefinitionData> findAll() {

        LOG.debug("> findAll");

        List<EntityDefinition> entityDefinitions = entityDefinitionService.findAll();

        List<EntityDefinitionData> results = new ArrayList<>();

        LOG.debug("= findAll: found {} results", entityDefinitions.size());

        if(entityDefinitions.isEmpty()) {

            return results;
        }

        for(EntityDefinition entityDefinition: entityDefinitions) {
            results.add(
                    entityDefinitionObjectMapper.toDataObject(entityDefinition)
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

        return entityDefinitionObjectMapper.toDataObject(fieldDefinition);
    }

    public List<FieldTypeData> getFieldTypes() {

        LOG.debug("> getFieldTypes");

        List<FieldType> fieldTypes = fieldTypeService.findAll();

        LOG.debug("= getFieldTypes: found {} results", fieldTypes.size());

        List<FieldTypeData> results = new ArrayList<>();

        if(fieldTypes.isEmpty()) {

            return results;
        }

        for(FieldType fieldType: fieldTypes) {
            results.add(
                    entityDefinitionObjectMapper.toDataObject(fieldType)
            );
        }

        return results;
    }
}
