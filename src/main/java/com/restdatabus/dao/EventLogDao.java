package com.restdatabus.dao;

import com.restdatabus.events.EventLog;

import java.util.List;

public interface EventLogDao {

    EventLog insert(EventLog eventLog);

    List<EventLog> findAll();
}
