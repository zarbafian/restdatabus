package com.restdatabus.model.service;

import com.restdatabus.model.meta.FieldDefinition;

/**
 * Handle persistence service for field definitions.
 */
public interface FieldDefinitionService {

    FieldDefinition create(FieldDefinition fieldDefinition);

    void delete(Long id);

    void deleteByEntityDefinition(Long id);

    FieldDefinition update(FieldDefinition fieldDefinition);

    FieldDefinition findByDefinitionAndName(Long entityDefinitionId, String name1);

    FieldDefinition findById(Long id);
}
