package com.restdatabus.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IsUpController {

    private static final Logger LOG = LoggerFactory.getLogger(IsUpController.class);

    private static final String OK = "OK";

    @RequestMapping(
            value = "/api/status",
            method = RequestMethod.GET
    )
    public ResponseEntity<String> status() {

        LOG.debug("-----------------------status-----------------------");

        return new ResponseEntity<String>(OK, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/admin/status",
            method = RequestMethod.GET
    )
    public ResponseEntity<String> statusAdmin() {

        LOG.debug("-----------------------statusAdmin-----------------------");

        return new ResponseEntity<String>(OK, HttpStatus.OK);
    }
}
