package com.restdatabus.model.service;

import com.restdatabus.model.meta.EntityDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Handle persistence service for entity definitions.
 */
@Service
public class EntityDefinitionServiceBean implements EntityDefinitionService {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionServiceBean.class);

    public EntityDefinition create(EntityDefinition entityDefinition) {

        LOG.debug("create: {}", entityDefinition);

        return null;
    }

    @Override
    public EntityDefinition findByName(String name) {

        LOG.debug("findByName: {}", name);

        return null;
    }
}
