package com.restdatabus.business.api.impl;

import com.restdatabus.events.EventLog;
import com.restdatabus.model.data.dvo.EventLogData;
import com.restdatabus.model.data.transform.EntityObjectMapper;
import com.restdatabus.model.service.EventLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InternalEventLogManagerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(InternalEventLogManagerImpl.class);

    @Autowired
    private EventLogService eventLogService;

    @Autowired
    private EntityObjectMapper entityObjectMapper;

    public List<EventLogData> findAll() {

        LOG.debug("findAll");

        List<EventLog> eventLogs = eventLogService.findAll();

        List<EventLogData> data = new ArrayList<>();

        for(EventLog el: eventLogs) {
            data.add(entityObjectMapper.toDataObject(el));
        }

        return data;
    }
}
