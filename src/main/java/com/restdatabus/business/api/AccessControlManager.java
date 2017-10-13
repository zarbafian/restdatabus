package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;

import java.io.Serializable;

public interface AccessControlManager {

    void hasPermission(Serializable authorizationResource, Action authorization);
}
