package com.restdatabus.business.api;

import com.restdatabus.events.EventLogType;

import java.time.OffsetDateTime;

public interface EventNotificationManager {

    void log(EventLogType logType, OffsetDateTime timestamp, String[] target, Object[] params);
}
