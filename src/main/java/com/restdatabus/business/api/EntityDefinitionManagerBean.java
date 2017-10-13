package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.transform.EntityDefinitionHelper;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.service.EntityDefinitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityDefinitionManagerBean implements EntityDefinitionManager {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionManagerBean.class);

    @Autowired
    private EventNotificationManager eventNotificationManager;

    @Autowired
    private AccessControlManager accessControlManager;

    @Autowired
    private EntityDefinitionService entityDefinitionService;

    @Autowired
    private AccessControlManager securityManager;

    public EntityDefinitionData create(EntityDefinitionData data) {

        LOG.debug("create: {}", data);

        // Check permissions
        accessControlManager.hasPermission("/definitions", Action.CREATE);

        // Check if the name not already exist
        EntityDefinition entityDefinition = entityDefinitionService.findByName(data.getName());

        if (entityDefinition != null) {
            String msg = "the name '" + data.getName() + "' already exists";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // Transform to a persistable form
        entityDefinition = EntityDefinitionHelper.dvoToPersist(data);

        // Persist
        EntityDefinition persisted = entityDefinitionService.create(entityDefinition);

        // Notify event
        eventNotificationManager.push(
                "/definitions",
                Action.CREATE,
                null,
                persisted
        );

        // TODO: transform back to DVO
        EntityDefinitionData persistedData = null;

        return persistedData;
    }
}
