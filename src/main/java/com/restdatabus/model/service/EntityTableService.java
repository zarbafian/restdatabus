package com.restdatabus.model.service;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;

public interface EntityTableService {

    void createTable(EntityDefinition entityDefinition);

    void addColumn(FieldDefinition fieldDefinition, String dataType);
}
