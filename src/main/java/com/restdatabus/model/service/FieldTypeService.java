package com.restdatabus.model.service;

import com.restdatabus.model.meta.FieldType;

import java.util.List;

public interface FieldTypeService {

    FieldType findByKey(String key);

    FieldType findById(Long id);

    List<FieldType> findAll();
}
