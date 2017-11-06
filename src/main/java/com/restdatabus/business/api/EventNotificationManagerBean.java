package com.restdatabus.business.api;

import com.restdatabus.events.EventLog;
import com.restdatabus.events.EventLogType;
import com.restdatabus.model.service.EventLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Service
public class EventNotificationManagerBean implements EventNotificationManager {

    private static final Logger LOG = LoggerFactory.getLogger(EventNotificationManagerBean.class);

    @Autowired
    private EventLogService eventLogService;

    @Override
    public void log(EventLogType logType, OffsetDateTime timestamp, String[] target, Object[] params) {

        LOG.debug("> log: logType={} target={} params: {}", logType, target, Arrays.toString(params));

        EventLog eventLog = new EventLog();
        eventLog.setType(logType);
        eventLog.setTimestamp(timestamp);
        eventLog.setPaths(Arrays.asList(target));
        eventLog.setParams(Arrays.asList(params));

        EventLog persistedEventLog = eventLogService.create(eventLog);

        LOG.debug("< log: {}", persistedEventLog);
    }
}
