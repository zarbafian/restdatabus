package com.restdatabus.business.api.impl;

import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityDefinitionImplHelper {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionImplHelper.class);

    /**
     * Verify that entity definition name is not empty
     * @param data
     */
    protected static void checkEntityName(EntityDefinitionData data) {

        if(data.getName() == null || data.getName().isEmpty()) {
            String msg = entityNameCannotBeEmpty();
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Verify that field definition name is not empty
     * @param data
     */
    protected static void checkFieldName(FieldDefinitionData data) {

        if(data.getName() == null || data.getName().isEmpty()) {
            String msg = fieldNameCannotBeEmpty();
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }

    private static String entityNameCannotBeEmpty = "the entity name cannot be empty";
    private static String fieldNameCannotBeEmpty = "the field name cannot be empty";
    private static String theEntity = "the entity '";
    private static String doesNotExist = "' does not exist";
    private static String alreadyExist = "' already exist";
    private static String alreadyHasAField = "' already has a field '";
    private static String doesNotHaveAField = "' does not have a field '";
    private static String endQuote = "'";

    protected static String fieldDoesNotExist(String name, String field){
        return theEntity + name + doesNotHaveAField + field + endQuote;
    }
    protected static String fieldAlreadyExist(String name, String field){
        return theEntity + name + alreadyHasAField + field + endQuote;
    }
    protected static String entityAlreadyExist(String name){
        return theEntity + name + alreadyExist;
    }
    protected static String entityDoesNotExist(String name){
        return theEntity + name + doesNotExist;
    }
    protected static String fieldNameCannotBeEmpty() {
        return fieldNameCannotBeEmpty;
    }
    protected static String entityNameCannotBeEmpty() {
        return entityNameCannotBeEmpty;
    }
}
