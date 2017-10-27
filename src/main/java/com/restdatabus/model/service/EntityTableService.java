package com.restdatabus.model.service;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;

public interface EntityTableService {

    void createTable(EntityDefinition entityDefinition);

    void deleteTable(EntityDefinition entityDefinition);

    void addColumn(FieldDefinition fieldDefinition, String dataType);

    void changeColumnType(FieldDefinition fieldDefinition, String newDataType);

    void removeColumn(FieldDefinition fieldDefinition);

    void addIndexedForeignKey(FieldDefinition fieldDefinition);

    void removeIndexedForeignKey(FieldDefinition fieldDefinition);
}
