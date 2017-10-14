package com.restdatabus.model.service;

import com.restdatabus.model.data.Entity;

/**
 * Handle persistence service for entities.
 */
public interface EntityService {

    Entity create(Entity entity);
}
