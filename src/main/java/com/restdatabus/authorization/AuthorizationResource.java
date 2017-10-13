package com.restdatabus.authorization;

/**
 * Base class for elements that require access control.
 */
public interface AuthorizationResource {

    String getUniqueId();
    String getType();
}
