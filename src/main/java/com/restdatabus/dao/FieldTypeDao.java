package com.restdatabus.dao;

import com.restdatabus.model.meta.FieldType;

import java.util.List;

public interface FieldTypeDao {

    FieldType findByKey(String key);

    FieldType findById(Long id);

    List<FieldType> findAll();
}
