package com.restdatabus.model.service;

import com.restdatabus.model.data.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle persistence service for entities.
 */
public interface EntityService {

    Entity create(Entity entity);
}
