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

            LOG.debug("Adding entity field for entity: {}", fieldDefinition.getTargetEntityId() );

            addEntityField(persistedField);
        }

        return persistedField;
    }

    private void addEntityField(FieldDefinition fieldDefinition) {

        // Add foreign key - not null
        RelationDefinition relationDefinition = new RelationDefinition();
        relationDefinition.setOriginField(fieldDefinition.getId());
        relationDefinition.setTargetEntity(fieldDefinition.getTargetEntityId());
        relationDefinitionService.insert(relationDefinition);

        // Add integrity constraint
        entityTableService.addIndexedForeignKey(fieldDefinition);
    }

    public void delete(FieldDefinition fieldDefinition) {

        LOG.debug("> delete: {}", fieldDefinition);

        // Remove relation if applicable
        FieldType fieldType = fieldTypeService.findById(fieldDefinition.getFieldTypeId());
        if(fieldType.getKey().equals(Constants.FIELD_TYPE_ENTITY)) {

            LOG.debug("Deleted entity field for entity: {}", fieldDefinition.getTargetEntityId() );

            removeEntityField(fieldDefinition);
        }

        // Delete column
        entityTableService.removeColumn(fieldDefinition);

        // Delete field definition
        fieldDefinitionService.delete(fieldDefinition.getId());

        LOG.debug("< delete: {}", fieldDefinition);
    }

    private void removeEntityField(FieldDefinition fieldDefinition) {

        RelationDefinition relationDefinition = relationDefinitionService.findByField(fieldDefinition.getId());

        if(relationDefinition == null) {
            String msg = "relation definition is null for field " + fieldDefinition.getId();
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        fieldDefinition.setTargetEntityId(relationDefinition.getTargetEntity());

        //RelationDefinition relationDefinition =
        relationDefinitionService.deleteByFieldId(fieldDefinition.getId());

        // Remove foreign key and index
        entityTableService.removeIndexedForeignKey(fieldDefinition);
    }

    public FieldDefinition update(FieldDefinition newFieldDefinition) {

        LOG.debug("> update: {}", newFieldDefinition);

        // Check if change to or from entity type, or a change in target entity
        FieldDefinition existingField = fieldDefinitionService.findById(newFieldDefinition.getId());

        LOG.debug("> update (existing data): {}", existingField);

        // Load field type
        FieldType oldFieldType = fieldTypeService.findById(existingField.getFieldTypeId());
        FieldType newFieldType = fieldTypeService.findById(newFieldDefinition.getFieldTypeId());

        // Change in field type ?
        if( ! newFieldDefinition.getFieldTypeId().equals(existingField.getFieldTypeId()) ) {

            // 1 - Yes

            // 1.1 - Changed from basic to entity ?
            if (Constants.FIELD_TYPE_ENTITY.equals(newFieldType.getKey())) {

                // Yes
                LOG.debug("Updated to entity field for entity: {}", newFieldDefinition.getTargetEntityId());

                // Add specific stuff
                addEntityField(newFieldDefinition);
            }

            // 1.2 - Change from entity to basic ?
            else if(Constants.FIELD_TYPE_ENTITY.equals(oldFieldType.getKey())) {

                // Yes
                LOG.debug("Updated to basic field from entity: {}", existingField.getTargetEntityId());

                // Remove specific stuff
                removeEntityField(existingField);
            }

            // 1.3 - Change from basic to basic
            else {

                // Nothing specific to do
                LOG.debug("Updated from basic to basic");
            }

            // Change column
            String sqlType = newFieldType.getSqlType();
            entityTableService.changeColumnType(newFieldDefinition, sqlType);
        }
        else if(
                // Entity to entiy
                ( Constants.FIELD_TYPE_ENTITY.equals(newFieldType.getKey()) )
        &&
                ( Constants.FIELD_TYPE_ENTITY.equals(oldFieldType.getKey()) )
        &&
                ( ! existingField.getTargetEntityId().equals(newFieldDefinition.getTargetEntityId()) )
        ) {
            // 2 - Change in target entity type

            LOG.debug("Updated from entity to entity: {} -> {}", existingField.getTargetEntityId(), newFieldDefinition.getTargetEntityId());

            removeEntityField(existingField);
            addEntityField(newFieldDefinition);
        }

        // Persist changes
        FieldDefinition updatedField = fieldDefinitionService.update(newFieldDefinition);

        return updatedField;
    }
}
