package com.restdatabus.model.service;

import com.restdatabus.dao.EntityTableDao;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityTableServiceBean implements EntityTableService {

    private static final Logger LOG = LoggerFactory.getLogger(EntityTableServiceBean.class);

    @Autowired
    private EntityTableDao entityTableDao;

    @Override
    public void createTable(EntityDefinition entityDefinition) {

        LOG.debug("createTable: {}", entityDefinition);

        entityTableDao.createTable(entityDefinition);
    }

    @Override
    public void addColumn(FieldDefinition fieldDefinition, String dataType) {

        LOG.debug("addColumn: {} - {}", fieldDefinition, dataType);

        entityTableDao.addColumn(fieldDefinition, dataType);
    }
}
