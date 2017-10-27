package com.restdatabus.dao;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;

public interface EntityTableDao {

    void createTable(EntityDefinition entityDefinition);

    void deleteTable(EntityDefinition entityDefinition);

    void addColumn(FieldDefinition fieldDefinition, String dataType);

    void removeColumn(FieldDefinition fieldDefinition);

    void changeColumnType(FieldDefinition fieldDefinition, String newDataType);

    void addForeignKey(FieldDefinition fieldDefinition);

    void removeIndexedForeignKey(FieldDefinition fieldDefinition);
}
