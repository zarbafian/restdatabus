package com.restdatabus.business.api.impl;

import com.restdatabus.model.data.transform.EntityDefinitionObjectMapper;
import com.restdatabus.model.meta.FieldDefinition;
import com.restdatabus.model.meta.FieldType;
import com.restdatabus.model.meta.RelationDefinition;
import com.restdatabus.model.service.*;
import com.restdatabus.web.api.Constants;
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
    private RelationDefinitionService relationDefinitionService;

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

        // Create relation if applicable
        if(fieldType.getKey().equals(Constants.FIELD_TYPE_ENTITY)) {

            LOG.debug("____________________________________________________________________");
            LOG.debug("Adding entity field for entity: {}", fieldDefinition.getTargetEntityId() );
            LOG.debug("____________________________________________________________________");

            // Add foreign key - not null
            RelationDefinition relationDefinition = new RelationDefinition();
            relationDefinition.setOriginField(persistedField.getId());
            relationDefinition.setTargetEntity(fieldDefinition.getTargetEntityId());
            relationDefinitionService.insert(relationDefinition);

            // Add integrity constraint
            entityTableService.addIndexedForeignKey(persistedField);
        }

        return persistedField;
    }

    public void delete(FieldDefinition fieldDefinition) {

        LOG.debug("> delete: {}", fieldDefinition);

        // TODO
        // Remove relation if applicable
        FieldType fieldType = fieldTypeService.findById(fieldDefinition.getFieldTypeId());
        if(fieldType.getKey().equals(Constants.FIELD_TYPE_ENTITY)) {

            LOG.debug("____________________________________________________________________");
            LOG.debug("Deleted entity field for entity: {}", fieldDefinition.getTargetEntityId() );
            LOG.debug("____________________________________________________________________");


            RelationDefinition relationDefinition = relationDefinitionService.findByField(fieldDefinition.getId());
            if(relationDefinition == null) {
                String msg = "relation definition is null for field " + fieldDefinition.getId();
                LOG.error(msg);
                throw new IllegalStateException(msg);
            }

            fieldDefinition.setTargetEntityId(relationDefinition.getTargetEntity());

            //RelationDefinition relationDefinition =
            relationDefinitionService.deleteByFieldId(fieldDefinition.getId());

            // Remove foreign key and index
            entityTableService.removeIndexedForeignKey(fieldDefinition);
        }

        // Delete column
        entityTableService.removeColumn(fieldDefinition);

        // Delete field definition
        fieldDefinitionService.delete(fieldDefinition.getId());

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
        if(fieldType.getKey().equals(Constants.FIELD_TYPE_ENTITY)) {
            LOG.debug("____________________________________________________________________");
            LOG.debug("Updated entity field for entity: {}", fieldDefinition.getTargetEntityId() );
            LOG.debug("____________________________________________________________________");
        }

        return updatedField;
    }
}
