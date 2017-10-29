package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;
import com.restdatabus.business.api.impl.InternalEntityDefinitionManagerImpl;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import com.restdatabus.model.data.dvo.FieldTypeData;
import com.restdatabus.web.api.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityDefinitionManagerBean implements EntityDefinitionManager {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionManagerBean.class);

    @Autowired
    private InternalEntityDefinitionManagerImpl impl;

    @Autowired
    private EventNotificationManager eventNotificationManager;

    @Autowired
    private AccessControlManager accessControlManager;

    @Autowired
    private AccessControlManager securityManager;

    @Override
    public EntityDefinitionData create(EntityDefinitionData data) {

        LOG.debug("create: {}", data);

        // Check permissions
        accessControlManager.hasPermission("/definitions", Action.CREATE);

        EntityDefinitionData persistedData = this.impl.create(data);

        // Notify event
        eventNotificationManager.push(
                "/definitions",
                Action.CREATE,
                null,
                persistedData
        );

        return persistedData;
    }

    @Override
    public EntityDefinitionData update(String name, EntityDefinitionData data) {

        LOG.debug("update: {}", name, data);

        // Check permissions
        accessControlManager.hasPermission(
                "/definitions", // TODO: + "/" + name
                Action.UPDATE
        );

        EntityDefinitionData existingData = this.impl.findByName(name);

        if(existingData == null) {

            String msg = "update - entity '" + name + "' does not exist";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        EntityDefinitionData updatedData = this.impl.update(existingData, data);

        boolean dataChanged = !existingData.equals(updatedData);

        if(dataChanged) {

            // Notify event
            eventNotificationManager.push(
                    "/definitions", // TODO: + "/" + name
                    Action.UPDATE,
                    data,
                    updatedData
            );
        }

        return updatedData;
    }

    @Override
    public FieldDefinitionData createField(String name, FieldDefinitionData data) {

        LOG.debug("createField: {} -> {}", name, data);

        // Check permissions
        accessControlManager.hasPermission(
                "/definitions", // TODO: + "/" + name/fields
                Action.CREATE
        );

        FieldDefinitionData fieldDefinitionData = this.impl.createField(name, data);

        // Notify event
        eventNotificationManager.push(
                "/definitions", // TODO: + "/" + name/fields
                Action.CREATE,
                null,
                fieldDefinitionData // TODO: + name?
        );

        return fieldDefinitionData;
    }

    @Override
    public FieldDefinitionData updateField(String name, String field, FieldDefinitionData newData) {

        LOG.debug("updateField: {}.{} -> {}", name, field, newData);

        // Check permissions
        accessControlManager.hasPermission(
                "/definitions", // TODO: + "/" + name/fields/data.name
                Action.UPDATE
        );

        FieldDefinitionData existingField = this.impl.findByNameAndDefinition(name, field);

        FieldDefinitionData updatedData = this.impl.updateField(name, field, newData);

        boolean dataChanged = !existingField.equals(updatedData);

        if(dataChanged) {

            // Notify event
            eventNotificationManager.push(
                    "/definitions", // TODO: + "/" + name/fields/data.name
                    Action.UPDATE,
                    existingField,
                    updatedData // TODO: + name?
            );
        }

        return updatedData;
    }

    @Override
    public List<FieldTypeData> getFieldTypes() {

        LOG.debug("getFieldTypes");

        // Check permissions
        accessControlManager.hasPermission(
                Constants.FIELD_TYPES,
                Action.READ
        );

        List<FieldTypeData> fieldTypeDatas = this.impl.getFieldTypes();

        // Notify event
        eventNotificationManager.push(
                Constants.FIELD_TYPES,
                Action.READ,
                fieldTypeDatas,
                fieldTypeDatas
        );

        return fieldTypeDatas;
    }

    @Override
    public void deleteField(String name, String field) {

        LOG.debug("deleteField: {} -> {}", name, field);

        // Check permissions
        accessControlManager.hasPermission(
                "/definitions", // TODO: + "/" + name/fields/data.name
                Action.DELETE
        );

        boolean deleted = this.impl.deleteField(name, field);

        if(deleted) {

            // Notify event
            eventNotificationManager.push(
                    "/definitions", // TODO: + "/" + name/fields/data.name
                    Action.DELETE,
                    name + "." + field, // TODO
                    null
            );
        }
    }

    @Override
    public EntityDefinitionData findByName(String name) {

        LOG.debug("findByName: {}", name);

        // Check permissions
        accessControlManager.hasPermission(
                "/definitions", // TODO: + "/" + name
                Action.READ
        );

        EntityDefinitionData foundData = this.impl.findByName(name);

        // Notify event
        eventNotificationManager.push(
                "/definitions", // TODO: + "/" + name
                Action.READ,
                foundData,
                foundData
        );

        return foundData;
    }

    @Override
    public List<EntityDefinitionData> findAll() {

        LOG.debug("findAll");

        // Check permissions
        accessControlManager.hasPermission(
                "/definitions",
                Action.READ
        );

        List<EntityDefinitionData> foundData = this.impl.findAll();

        // Notify event
        eventNotificationManager.push(
                "/definitions",
                Action.READ,
                foundData,
                foundData
        );

        return foundData;
    }

    @Override
    public void deleteByName(String name) {

        LOG.debug("deleteByName: {}", name);

        // Check permissions
        accessControlManager.hasPermission(
                "/definitions", // TODO: + "/" + name
                Action.DELETE
        );

        boolean deleted = this.impl.deleteByName(name);

        if (deleted) {

            // Notify event
            eventNotificationManager.push(
                    "/definitions", // TODO: + "/" + name
                    Action.DELETE,
                    name,
                    null
            );
        }
    }
}
