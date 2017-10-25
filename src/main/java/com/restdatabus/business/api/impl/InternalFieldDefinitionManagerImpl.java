package com.restdatabus.business.api.impl;

import com.restdatabus.model.data.transform.EntityDefinitionObjectMapper;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import com.restdatabus.model.meta.FieldType;
import com.restdatabus.model.service.EntityDefinitionService;
import com.restdatabus.model.service.EntityTableService;
import com.restdatabus.model.service.FieldDefinitionService;
import com.restdatabus.model.service.FieldTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handle field related logic for InternalEntityDefinitionManagerImpl.
 */
@Service
public class InternalFieldDefinitionManagerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(InternalFieldDefinitionManagerImpl.class);

    @Autowired
    private EntityDefinitionService entityDefinitionService;

    @Autowired
    private FieldDefinitionService fieldDefinitionService;

    @Autowired
    private FieldTypeService fieldTypeService;

    @Autowired
    EntityDefinitionObjectMapper entityDefinitionObjectMapper;

    @Autowired
    private EntityTableService entityTableService;

    public FieldDefinition create(FieldDefinition fieldDefinition) {

        LOG.debug("create: {}", fieldDefinition);

        // Create field
        FieldDefinition persistedField = fieldDefinitionService.create(fieldDefinition);

        // Create column
        FieldType fieldType = fieldTypeService.findById(persistedField.getFieldTypeId());
        String sqlType = fieldType.getSqlType();
        entityTableService.addColumn(persistedField, sqlType);

        // TODO
        // Create relation if applicable

        return persistedField;
    }

    public void delete(FieldDefinition fieldDefinition) {

        LOG.debug("> delete: {}", fieldDefinition);

        // Delete field definition
        fieldDefinitionService.delete(fieldDefinition.getId());

        // TODO
        // Remove relation if applicable

        // Delete column
        entityTableService.removeColumn(fieldDefinition);

        LOG.debug("< delete: {}", fieldDefinition);
    }

    public FieldDefinition update(FieldDefinition fieldDefinition) {

        LOG.debug("> update: {}", fieldDefinition);

        // Persist changes
        FieldDefinition updatedField = fieldDefinitionService.update(fieldDefinition);

        // Change column
        FieldType fieldType = fieldTypeService.findById(updatedField.getFieldTypeId());
        String sqlType = fieldType.getSqlType();
        entityTableService.changeColumnType(updatedField, sqlType);

        // TODO
        // Add or remove relation if applicable

        return updatedField;
    }
}
