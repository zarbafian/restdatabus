package com.restdatabus.dao;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;

import java.util.List;

public interface FieldDefinitionDao {

    FieldDefinition insert(FieldDefinition entity);

    List<FieldDefinition> findByEntityDefinition(EntityDefinition entityDefinition);

    void delete(Long id);

    void deleteByEntityDefinition(Long id);

    FieldDefinition update(FieldDefinition fieldDefinition);

    FieldDefinition findByDefinitionAndName(Long entityName, String fieldName);
}
