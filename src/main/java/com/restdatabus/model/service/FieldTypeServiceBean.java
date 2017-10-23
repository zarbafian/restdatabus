package com.restdatabus.model.service;

import com.restdatabus.dao.FieldTypeDao;
import com.restdatabus.model.meta.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldTypeServiceBean implements FieldTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(FieldTypeServiceBean.class);

    @Autowired
    private FieldTypeDao fieldTypeDao;

    @Override
    public FieldType findById(Long id) {

        LOG.debug("findById: {}", id);

        return fieldTypeDao.findById(id);
    }

    @Override
    public FieldType findByKey(String key) {

        LOG.debug("findByKey: {}", key);

        return fieldTypeDao.findByKey(key);
    }

    @Override
    public List<FieldType> findAll() {

        LOG.debug("findAll");

        return fieldTypeDao.findAll();
    }
}
