package com.restdatabus.dao;

import com.restdatabus.model.meta.EntityDefinition;

import java.util.List;

public interface EntityDefinitionDao {

    EntityDefinition insert(EntityDefinition entity);

    EntityDefinition findByName(String name);

    List<EntityDefinition> findAll();

    void delete(Long id);

    EntityDefinition update(EntityDefinition entityDefinition);

    EntityDefinition findById(Long id);
}
