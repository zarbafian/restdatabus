package com.restdatabus.business.api;

import static com.restdatabus.events.EventLogTarget.*;

import com.restdatabus.authorization.Action;
import com.restdatabus.business.api.impl.InternalEntityManagerImpl;
import com.restdatabus.events.EventLogType;
import com.restdatabus.model.data.dvo.EntityData;
import com.restdatabus.model.service.TimeService;
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
    private TimeService timeService;

    @Autowired
    private EventNotificationManager eventNotificationManager;

    public EntityData create(EntityData data) {

        LOG.debug("create: {}", data);

        // Check permissions
        accessControlManager.hasPermission("/entities/" + data.getType(), Action.CREATE); // TODO

        EntityData persistedData = this.impl.create(data);

        // Notify event
        eventNotificationManager.log(
                EventLogType.CREATE,
                timeService.now(),
                new String [] { ENTITIES, data.getType() },
                new Object[] { data, persistedData }
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
        eventNotificationManager.log(
                EventLogType.READ,
                timeService.now(),
                new String [] { ENTITIES, type },
                new Object[] {}
        );

        return results;
    }

    @Override
    public EntityData findByTypeAndId(String type, Long id) {

        LOG.debug("findByTypeAndId: {}", type);

        // Check permissions
        accessControlManager.hasPermission("/entities/" + type + "/" + id, Action.READ); // TODO

        EntityData result = this.impl.findByTypeAndId(type, id);

        // Notify event
        eventNotificationManager.log(
                EventLogType.READ,
                timeService.now(),
                new String [] { ENTITIES, type, String.valueOf(id) },
                new Object[] {}
        );

        return result;
    }

    @Override
    public void deleteByTypeAndId(String type, Long id) {

        LOG.debug("deleteByTypeAndId: {}, {}", type, id);

        // Check permissions
        accessControlManager.hasPermission("/entities/" + type + "/" + id, Action.DELETE); // TODO

        boolean found = this.impl.deleteByTypeAndId(type, id);

        if(found) {
            // Notify event
            eventNotificationManager.log(
                    EventLogType.DELETE,
                    timeService.now(),
                    new String [] { ENTITIES, type, String.valueOf(id) },
                    new Object[] {}
            );
        }
    }

    @Override
    public EntityData update(String type, Long id, EntityData data) {

        LOG.debug("update: {}.{} -> {}", type, id, data);

        // Check permissions
        accessControlManager.hasPermission("/entities/" + type + "/" + id, Action.UPDATE); // TODO

        EntityData existingData = this.impl.findByTypeAndId(type, id);

        EntityData updatedData = this.impl.update(type, id, data);

        boolean dataChanged = !existingData.equals(updatedData);

        if(dataChanged) {

            // Notify event
            eventNotificationManager.log(
                    EventLogType.UPDATE,
                    timeService.now(),
                    new String [] { ENTITIES, type, String.valueOf(id) },
                    new Object[] { existingData, updatedData }
            );
        }

        return updatedData;
    }
}
