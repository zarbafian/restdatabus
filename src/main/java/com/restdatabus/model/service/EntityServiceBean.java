package com.restdatabus.model.service;

import com.restdatabus.model.data.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle persistence service for entities.
 */
public class EntityServiceBean {

    private static final Logger LOG = LoggerFactory.getLogger(EntityServiceBean.class);

    public Entity create(Entity entity) {

        LOG.debug("create: {}", entity);

        entity.setId(System.currentTimeMillis());

        return entity;
    }
}
