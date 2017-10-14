package com.restdatabus.model.service;

import com.restdatabus.model.meta.EntityDefinition;

import java.util.List;

/**
 * Handle persistence service for entity definitions.
 */
public interface EntityDefinitionService {

    EntityDefinition create(EntityDefinition entityDefinition);

    EntityDefinition findByName(String name);

    List<EntityDefinition> findAll();
}
