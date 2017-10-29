package com.restdatabus.business.api.impl;

import com.restdatabus.model.data.dvo.EntityData;
import org.slf4j.Logger;

public class EntityImplHelper {

    /**
     * Verify that name is not empty
     * @param LOG
     * @param data
     */
    protected static void checkEntityType(Logger LOG, EntityData data) {

        if(data.getType() == null || data.getType().isEmpty()) {
            String msg = "mandatory field entity type is empty";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }
}
