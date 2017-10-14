package com.restdatabus.model.service;

import com.restdatabus.dao.FieldDefinitionDao;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handle persistence service for field definitions.
 */
@Service
public class FieldDefinitionServiceBean implements FieldDefinitionService {

    private static final Logger LOG = LoggerFactory.getLogger(FieldDefinitionServiceBean.class);

    @Autowired
    private FieldDefinitionDao definitionDao;

    public FieldDefinition create(FieldDefinition fieldDefinition) {

        LOG.debug("create: {}", fieldDefinition);

        return definitionDao.insert(fieldDefinition);
    }

    @Override
    public void delete(Long id) {

        LOG.debug("delete: {}", id);

        definitionDao.delete(id);
    }

    @Override
    public void deleteByEntityDefinition(Long id) {

        LOG.debug("deleteByEntityDefinition: {}", id);

        definitionDao.deleteByEntityDefinition(id);
    }

    @Override
    public FieldDefinition update(FieldDefinition fieldDefinition) {

        LOG.debug("update: {}", fieldDefinition);

        return definitionDao.update(fieldDefinition);
    }

    @Override
    public FieldDefinition findByDefinitionAndName(Long entityDefinitionId, String fieldName) {

        LOG.debug("findByDefinitionAndName: {} -> {}", entityDefinitionId, fieldName);

        return definitionDao.findByDefinitionAndName(entityDefinitionId, fieldName);
    }
}
