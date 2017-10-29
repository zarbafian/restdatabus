package com.restdatabus.model.service;

import com.restdatabus.model.meta.EntityDefinition;

import java.util.List;
import java.util.Map;

public interface EntityValidationService {

    List<String> verify(Map<String, Object> data, EntityDefinition definition);
}
