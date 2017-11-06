package com.restdatabus.web.api;

import java.time.ZoneId;

public class Constants {

    private Constants() {}

    // TODO: FIXME
    public static final ZoneId TIME_ZONE = ZoneId.of("Europe/Paris");


    public static final String ID = "id";

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
    public static final String ENTITIES_BY_TYPE = ENTITIES + "/{name}";
    public static final String ENTITY_BY_TYPE_AND_ID = ENTITIES_BY_TYPE + "/{id}";

    // API - Reports
    public static final String REPORTS = API + "/reports";
    public static final String ENTITIES_REPORT = REPORTS + "/entities";
    public static final String ENTITY_TYPE_REPORT = ENTITIES_REPORT + "/{name}";

    // API - Event logs
    public static final String EVENTS = API + "/events";
}
