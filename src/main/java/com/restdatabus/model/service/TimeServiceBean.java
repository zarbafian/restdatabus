package com.restdatabus.model.service;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class TimeServiceBean implements TimeService {

    public OffsetDateTime now() {
        return OffsetDateTime.now();
    }
}
