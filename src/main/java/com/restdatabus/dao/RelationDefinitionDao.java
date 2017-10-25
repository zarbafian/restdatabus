package com.restdatabus.dao;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.RelationDefinition;

import java.util.List;

public interface RelationDefinitionDao {

    RelationDefinition insert(RelationDefinition relationDefinition);

    RelationDefinition update(RelationDefinition relationDefinition);

    RelationDefinition findBySourceAndTarget(Long source, Long target);

    List<RelationDefinition> findByEntityDefinition(EntityDefinition entityDefinition);

    void deleteByFieldId(Long fieldDefId);

    void delete(Long id);
}
