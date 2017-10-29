package com.restdatabus.authorization;

public enum Action {

    CREATE("action.create"),
    READ("action.read"),
    UPDATE("action.update"),
    DELETE("action.delete"),
    EXECUTE("action.execute");

    private final String text;

    private Action(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
