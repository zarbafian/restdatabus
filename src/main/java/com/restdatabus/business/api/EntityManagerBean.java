package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;
import com.restdatabus.business.api.impl.InternalEntityManagerImpl;
import com.restdatabus.model.data.dvo.EntityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityManagerBean implements EntityManager {

    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerBean.class);

    @Autowired
    private InternalEntityManagerImpl impl;

    @Autowired
    private AccessControlManager accessControlManager;

    @Autowired
    private EventNotificationManager eventNotificationManager;

    public EntityData create(EntityData data) {

        LOG.debug("create: {}", data);

        // Check permissions
        accessControlManager.hasPermission("/entities/" + data.getType(), Action.CREATE); // TODO

        EntityData persistedData = this.impl.create(data);

        // Notify event
        eventNotificationManager.push(
                "/entities/" + data.getType(), // TODO
                Action.CREATE,
                null,
                persistedData
        );

        return persistedData;
    }

    @Override
    public List<EntityData> findByType(String type) {

        LOG.debug("findByType: {}", type);

        // Check permissions
        accessControlManager.hasPermission("/entities/" + type, Action.READ); // TODO

        List<EntityData> results = this.impl.findByType(type);

        // Notify event
        eventNotificationManager.push(
                "/entities/" + type, // TODO
                Action.READ,
                null,
                results
        );

        return results;
    }
}
