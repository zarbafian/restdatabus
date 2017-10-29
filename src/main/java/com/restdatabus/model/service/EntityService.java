package com.restdatabus.model.service;

import com.restdatabus.model.data.Entity;
import com.restdatabus.model.meta.EntityDefinition;

import java.util.List;

/**
 * Handle persistence service for entities.
 */
public interface EntityService {

    Entity create(EntityDefinition entityDefinition, Entity entity);

    List<Entity> findByDefinition(EntityDefinition entityDefinition);

    Entity findByDefinitionAndId(EntityDefinition entityDefinition, Long id);

    void deleteByDefinitionAndId(EntityDefinition entityDefinition, Long id);
}
