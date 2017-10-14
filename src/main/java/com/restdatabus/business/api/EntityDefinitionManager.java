package com.restdatabus.business.api;

import com.restdatabus.model.data.dvo.EntityDefinitionData;

import java.util.List;

public interface EntityDefinitionManager {

    EntityDefinitionData create(EntityDefinitionData data);

    EntityDefinitionData findByName(String name);

    List<EntityDefinitionData> findAll();
}
