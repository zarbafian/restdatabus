package com.restdatabus.model.service;

import com.restdatabus.dao.FieldDefinitionDao;
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

}
