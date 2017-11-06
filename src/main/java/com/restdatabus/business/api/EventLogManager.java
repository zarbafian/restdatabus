package com.restdatabus.business.api;

import com.restdatabus.model.data.dvo.EventLogData;

import java.util.List;

public interface EventLogManager {

    List<EventLogData> findAll();
}
