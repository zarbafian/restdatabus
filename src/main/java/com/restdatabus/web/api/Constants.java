package com.restdatabus.web.api;

public class Constants {

    private Constants() {}

    // Field types
    public static final String FIELD_TYPE_ENTITY = "entity";

    public static final String API = "/api";
    public static final String ADMIN = "/admin";

    // Status
    public static final String STATUS = "/status";
    public static final String USER_STATUS = API + STATUS;
    public static final String ADMIN_STATUS = ADMIN + STATUS;

    // API - Definitions and fields
    public static final String DEFINITIONS = API + "/definitions";
    public static final String FIELD_TYPES = API + "/fieldtypes";
    public static final String DEFINITION_BY_NAME = DEFINITIONS + "/{name}";
    public static final String FIELDS_BY_NAME = DEFINITION_BY_NAME + "/fields";
    public static final String FIELD_BY_NAME_AND_FIELD = FIELDS_BY_NAME + "/{field}";

    // API - User
    public static final String PRINCIPAL = API + "/principal";

    // API - Entity
    public static final String ENTITIES = API + "/entities";
    public static final String ENTITY_BY_ID = ENTITIES + "/{id}";
    public static final String FILED_BY_ENTITY_AND_ID = ENTITIES + "/{id}/{name}";
}
