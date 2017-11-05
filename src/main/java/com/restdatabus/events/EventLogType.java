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
}
