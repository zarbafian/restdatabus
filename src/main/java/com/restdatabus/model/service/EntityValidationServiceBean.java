package com.restdatabus.model.service;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EntityValidationServiceBean implements EntityValidationService {

    private static final Logger LOG = LoggerFactory.getLogger(EntityValidationServiceBean.class);

    @Override
    public List<String> verify(Map<String, Object> data, EntityDefinition definition) {

        LOG.debug("verify: {} --(against)--> {}", data, definition);

        List<String> errors = new ArrayList<>();

        data.forEach( (key, value) -> {

            FieldDefinition fieldDefinition = definition.findFieldByName(key);

            if(fieldDefinition == null) {
                String msg = "the field '" + key + "' does not exist in entity '" + definition.getName();
                LOG.error(msg);
                errors.add(msg);
            }
        });

        return errors;
    }
}
