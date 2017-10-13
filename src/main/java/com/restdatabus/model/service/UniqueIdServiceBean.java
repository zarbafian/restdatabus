package com.restdatabus.model.service;

import java.util.concurrent.atomic.AtomicLong;

public class UniqueIdServiceBean {

    private AtomicLong atomicLong;
    public Long nextId() {
        return atomicLong.incrementAndGet();
    }
}
