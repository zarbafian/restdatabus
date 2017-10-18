package com.restdatabus.web.api;

import static com.restdatabus.web.api.Constants.USER_STATUS;
import static com.restdatabus.web.api.Constants.ADMIN_STATUS;

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
            value = USER_STATUS,
            method = RequestMethod.GET
    )
    public ResponseEntity<String> status() {

        LOG.debug("-----------------------status-----------------------");

        return new ResponseEntity<>(OK, HttpStatus.OK);
    }

    @RequestMapping(
            value = ADMIN_STATUS,
            method = RequestMethod.GET
    )
    public ResponseEntity<String> statusAdmin() {

        LOG.debug("-----------------------statusAdmin-----------------------");

        return new ResponseEntity<>(OK, HttpStatus.OK);
    }
}
