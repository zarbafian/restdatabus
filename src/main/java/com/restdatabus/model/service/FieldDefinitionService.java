package com.restdatabus.model.service;

import com.restdatabus.model.meta.FieldDefinition;

/**
 * Handle persistence service for field definitions.
 */
public interface FieldDefinitionService {

    FieldDefinition create(FieldDefinition fieldDefinition);
}
