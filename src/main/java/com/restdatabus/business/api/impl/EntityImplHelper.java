package com.restdatabus.business.api.impl;

import com.restdatabus.model.data.dvo.EntityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityImplHelper {

    private final static Logger LOG = LoggerFactory.getLogger(EntityImplHelper.class);

    /**
     * Verify that name is not empty
     * @param data
     */
    protected static void checkEntityType(EntityData data) {

        if(data.getType() == null || data.getType().isEmpty()) {
            String msg = "mandatory parameter entity type is empty";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }
}
