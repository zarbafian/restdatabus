package com.restdatabus.dao;

import com.restdatabus.model.data.Entity;
import com.restdatabus.model.meta.EntityDefinition;

import java.util.List;

public interface EntityDao {

    Entity insert(EntityDefinition entityDefinition, Entity entity);

    List<Entity> findByDefinition(EntityDefinition entityDefinition);
}
