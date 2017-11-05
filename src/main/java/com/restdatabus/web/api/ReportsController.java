package com.restdatabus.web.api;

import static com.restdatabus.web.api.Constants.*;

import com.restdatabus.business.api.ReportManager;
import com.restdatabus.model.data.dvo.ReportData;
import com.restdatabus.model.data.dvo.ReportResultsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportsController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportsController.class);

    @Autowired
    private ReportManager reportManager;

    @RequestMapping(
            value = ENTITIES_REPORT,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ReportData>> getReports() {

        LOG.debug("getReports");

        List<ReportData> results = reportManager.getEntityReports();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @RequestMapping(
            value = ENTITY_TYPE_REPORT,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ReportResultsData> getEntityReport(@PathVariable(value = "name") String type) {

        LOG.debug("getEntityReport");

        ReportResultsData results = reportManager.getEntityReport(type);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
