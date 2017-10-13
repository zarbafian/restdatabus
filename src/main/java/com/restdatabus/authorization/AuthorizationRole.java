package com.restdatabus.authorization;

/**
 * The computed role of the authorization entity.
 */
public enum AuthorizationRole {

    USER("user-role.user"),
    GROUP("user-role.group"),
    OTHER("user-role.other");

    private final String text;

    private AuthorizationRole(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
