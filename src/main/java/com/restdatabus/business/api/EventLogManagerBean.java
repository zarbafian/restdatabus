package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;
import com.restdatabus.business.api.impl.InternalEventLogManagerImpl;
import com.restdatabus.model.data.dvo.EventLogData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogManagerBean implements EventLogManager {

    private static final Logger LOG = LoggerFactory.getLogger(EventLogManagerBean.class);

    @Autowired
    private InternalEventLogManagerImpl impl;

    @Autowired
    private AccessControlManager accessControlManager;

    @Override
    public List<EventLogData> findAll() {

        LOG.debug("findAll");

        // Check permissions
        accessControlManager.hasPermission("/eventlogs", Action.READ); // TODO

        return impl.findAll();
    }
}
