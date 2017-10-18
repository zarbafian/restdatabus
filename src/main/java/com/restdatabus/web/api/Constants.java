package com.restdatabus.web.api;

public class Constants {

    private Constants() {}

    public static final String API = "/api";
    public static final String ADMIN = "/admin";

    // Status
    public static final String STATUS = "/status";
    public static final String USER_STATUS = API + STATUS;
    public static final String ADMIN_STATUS = ADMIN + STATUS;

    // Definitions and fields
    public static final String DEFINITIONS = API + "/definitions";
    public static final String DEFINITION_BY_NAME = DEFINITIONS + "/{name}";
    public static final String FIELDS_BY_NAME = API + DEFINITION_BY_NAME + "/fields";
    public static final String FIELD_BY_NAME_AND_FIELD = FIELDS_BY_NAME + "/{field}";

    // User
    public static final String PRINCIPAL = API + "/principal";

}
