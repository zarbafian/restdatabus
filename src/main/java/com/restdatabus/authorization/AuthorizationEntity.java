package com.restdatabus.authorization;

/**
 * Base class for elements that can authorize such as users and groups.
 */
public interface AuthorizationEntity {

    String getUniqueId();
    String getType();
}
