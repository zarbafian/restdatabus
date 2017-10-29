package com.restdatabus.business.api;

import com.restdatabus.model.data.dvo.EntityData;

import java.util.List;

public interface EntityManager {

    EntityData create(EntityData data);

    List<EntityData> findByType(String type);
}
