package com.restdatabus.model.service;

import com.restdatabus.events.EventLog;

public interface EventLogService {

    EventLog create(EventLog eventLog);
}
