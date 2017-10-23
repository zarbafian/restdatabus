package com.restdatabus.business.api;

import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import com.restdatabus.model.data.dvo.FieldTypeData;

import java.util.List;

public interface EntityDefinitionManager {

    EntityDefinitionData create(EntityDefinitionData data);

    EntityDefinitionData findByName(String name);

    List<EntityDefinitionData> findAll();

    void deleteByName(String name);

    EntityDefinitionData update(String name, EntityDefinitionData data);

    FieldDefinitionData createField(String name, FieldDefinitionData data);

    void deleteField(String name, String field);

    FieldDefinitionData updateField(String name, String field, FieldDefinitionData newData);

    List<FieldTypeData> getFieldTypes();
}
