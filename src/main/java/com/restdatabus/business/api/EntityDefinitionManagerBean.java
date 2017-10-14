package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;
import com.restdatabus.business.api.impl.InternalEntityDefinitionManagerImpl;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.service.EntityDefinitionService;
import com.restdatabus.model.service.FieldDefinitionService;
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
}
