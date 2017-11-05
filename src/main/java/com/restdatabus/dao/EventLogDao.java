package com.restdatabus.dao;

import com.restdatabus.events.EventLog;

public interface EventLogDao {

    EventLog insert(EventLog eventLog);
}
