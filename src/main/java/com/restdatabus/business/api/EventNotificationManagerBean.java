package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventNotificationManagerBean implements EventNotificationManager {

    private static final Logger LOG = LoggerFactory.getLogger(EventNotificationManagerBean.class);

    @Override
    public void push(String path, Action actionType, Object before, Object after) {

        LOG.debug("push: path={} actionType={}\nbefore: {}\nafter: {}", path, actionType, before, after);

    }
}
