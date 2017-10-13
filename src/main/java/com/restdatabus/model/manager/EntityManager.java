package com.restdatabus.model.manager;

import com.restdatabus.model.data.Entity;
import com.restdatabus.model.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manage business logic for entity creation.
 */
public class EntityManager {

    private static final Logger LOG = LoggerFactory.getLogger(EntityManager.class);

    private EntityService entityService;

    public EntityManager(EntityService entityService) {

        LOG.debug("construtor: {}", entityService);

        this.entityService = entityService;
    }

    public Entity create(Entity entity) {

        LOG.debug("create: {}", entity);

        Entity createdEntity = entityService.create(entity);

        return createdEntity;
    }
}
