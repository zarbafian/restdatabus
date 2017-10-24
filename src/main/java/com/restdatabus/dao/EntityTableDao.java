package com.restdatabus.dao;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;

public interface EntityTableDao {

    void createTable(EntityDefinition entityDefinition);

    void addColumn(FieldDefinition fieldDefinition, String dataType);
}
