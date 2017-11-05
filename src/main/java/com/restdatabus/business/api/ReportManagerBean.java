package com.restdatabus.business.api;

import static com.restdatabus.events.EventLogTarget.*;

import com.restdatabus.authorization.Action;
import com.restdatabus.business.api.impl.InternalReportManagerImpl;
import com.restdatabus.events.EventLogType;
import com.restdatabus.model.data.dvo.ReportData;
import com.restdatabus.model.data.dvo.ReportResultsData;
import com.restdatabus.model.service.TimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportManagerBean implements ReportManager {

    private static final Logger LOG = LoggerFactory.getLogger(ReportManagerBean.class);

    @Autowired
    private InternalReportManagerImpl impl;

    @Autowired
    private AccessControlManager accessControlManager;

    @Autowired
    private EventNotificationManager eventNotificationManager;

    @Autowired
    private TimeService timeService;

    @Override
    public List<ReportData> getEntityReports() {

        LOG.debug("getEntityReports");

        // Check permissions
        accessControlManager.hasPermission("/reports", Action.READ); // TODO

        List<ReportData> results = this.impl.getEntityReports();

        // Notify event
        eventNotificationManager.log(
                EventLogType.READ,
                timeService.now(),
                new String [] { REPORTS, ENTITIES },
                new Object[] {}
        );

        return results;
    }

    @Override
    public ReportResultsData getEntityReport(String type) {

        LOG.debug("getEntityReport: {}", type);

        // Check permissions
        accessControlManager.hasPermission("/reports/" + type, Action.READ); // TODO

        ReportResultsData results = this.impl.getEntityReport(type);

        // Notify event
        eventNotificationManager.log(
                EventLogType.READ,
                timeService.now(),
                new String [] { REPORTS, ENTITIES, type },
                new Object[] {}
        );

        return results;
    }
}
