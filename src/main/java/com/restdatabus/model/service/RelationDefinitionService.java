package com.restdatabus.model.service;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.RelationDefinition;

import java.util.List;

/**
 * Handle persistence service for relation definitions.
 */
public interface RelationDefinitionService {

    RelationDefinition insert(RelationDefinition relationDefinition);

    RelationDefinition update(RelationDefinition relationDefinition);

    RelationDefinition findBySourceAndTarget(Long source, Long target);

    /**
     * Provide a list of relation that are targeting target entity definition.
     * @param entityDefinition target entity definition
     * @return a list of found relations
     */
    List<RelationDefinition> findByEntityDefinition(EntityDefinition entityDefinition);

    void deleteByFieldId(Long fieldDefId);

    void delete(Long id);

}
