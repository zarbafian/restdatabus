package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;

public interface EventNotificationManager {

    void push(String path, Action actionType, Object before, Object after);
}
