package com.restdatabus.events;

public enum EventLogType {

    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete"),
    EXECUTE("execute");

    private String value;

    EventLogType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static EventLogType fromValue(String value) {

        switch (value) {
            case "create":
                return CREATE;
            case "read":
                return READ;
            case "update":
                return UPDATE;
            case "delete":
                return DELETE;
            case "execute":
                return EXECUTE;
            default:
                throw new IllegalArgumentException("unknown value for event log type: " + value);
        }
    }
}
