package com.restdatabus.model.service;

import com.restdatabus.events.EventLog;

import java.util.List;

public interface EventLogService {

    EventLog create(EventLog eventLog);

    List<EventLog> findAll();
}
