package com.restdatabus.web.api;

import static com.restdatabus.web.api.Constants.*;

import com.restdatabus.business.api.EventLogManager;
import com.restdatabus.model.data.dvo.EventLogData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {

    private static final Logger LOG = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    private EventLogManager eventLogManager;

    @RequestMapping(
            value = EVENTS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<EventLogData>> getEventLogs() {

        LOG.debug("getEventLogs");

        List<EventLogData> results = eventLogManager.findAll();

        return new ResponseEntity<List<EventLogData>>(results, HttpStatus.OK);

    }
}
