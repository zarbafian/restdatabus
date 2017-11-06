package com.restdatabus.model.service;

import com.restdatabus.dao.EventLogDao;
import com.restdatabus.events.EventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogServiceBean implements EventLogService {

    private static final Logger LOG = LoggerFactory.getLogger(EventLogServiceBean.class);

    @Autowired
    private EventLogDao eventLogDao;

    @Override
    public EventLog create(EventLog eventLog) {

        LOG.debug("create: {}", eventLog);

        return eventLogDao.insert(eventLog);
    }

    @Override
    public List<EventLog> findAll() {

        LOG.debug("findAll");

        return eventLogDao.findAll();
    }
}
