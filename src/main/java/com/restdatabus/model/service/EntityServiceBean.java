package com.restdatabus.model.service;

import com.restdatabus.dao.EntityDao;
import com.restdatabus.model.data.Entity;
import com.restdatabus.model.meta.EntityDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handle persistence service for entities.
 */
@Service
public class EntityServiceBean implements EntityService {

    private static final Logger LOG = LoggerFactory.getLogger(EntityServiceBean.class);

    @Autowired
    private EntityDao entityDao;

    public Entity create(EntityDefinition entityDefinition, Entity entity) {

        LOG.debug("create: {} -> {}", entityDefinition, entity);

        return entityDao.insert(entityDefinition, entity);
    }

    @Override
    public List<Entity> findByDefinition(EntityDefinition entityDefinition) {

        LOG.debug("findByDefinition: {}", entityDefinition);

        return entityDao.findByDefinition(entityDefinition);
    }
}
