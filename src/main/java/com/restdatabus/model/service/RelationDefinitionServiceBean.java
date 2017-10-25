package com.restdatabus.model.service;

import com.restdatabus.dao.RelationDefinitionDao;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.RelationDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handle persistence service for relation definitions.
 */
@Service
public class RelationDefinitionServiceBean implements RelationDefinitionService {

    private static final Logger LOG = LoggerFactory.getLogger(RelationDefinitionServiceBean.class);

    @Autowired
    private RelationDefinitionDao definitionDao;

    @Override
    public RelationDefinition insert(RelationDefinition relationDefinition) {

        LOG.debug("create: {}", relationDefinition);

        return definitionDao.insert(relationDefinition);
    }

    @Override
    public void delete(Long id) {

        LOG.debug("delete: {}", id);

        definitionDao.delete(id);
    }

    @Override
    public RelationDefinition update(RelationDefinition relationDefinition) {

        LOG.debug("update: {}", relationDefinition);

        return definitionDao.update(relationDefinition);
    }

    @Override
    public RelationDefinition findBySourceAndTarget(Long source, Long target) {

        LOG.debug("findBySourceAndTarget: {} -> {}", source, target);

        return definitionDao.findBySourceAndTarget(source, target);
    }

    @Override
    public List<RelationDefinition> findByEntityDefinition(EntityDefinition entityDefinition) {

        LOG.debug("findByEntityDefinition: {}", entityDefinition);

        return definitionDao.findByEntityDefinition(entityDefinition);
    }

    @Override
    public void deleteByFieldId(Long fieldDefId) {

        LOG.debug("deleteByFieldId: {}", fieldDefId);

        definitionDao.deleteByFieldId(fieldDefId);
    }
}
