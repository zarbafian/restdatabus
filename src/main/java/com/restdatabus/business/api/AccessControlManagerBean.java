package com.restdatabus.business.api;

import com.restdatabus.authorization.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class AccessControlManagerBean implements AccessControlManager {

    private static final Logger LOG = LoggerFactory.getLogger(AccessControlManagerBean.class);

    @Override
    public void hasPermission(Serializable authorizationResource, Action authorization) {

        LOG.debug("hasPermission: {} -> {}", authorizationResource, authorization);

    }
}
