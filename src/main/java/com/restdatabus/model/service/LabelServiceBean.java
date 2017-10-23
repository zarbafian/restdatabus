package com.restdatabus.model.service;

import org.springframework.stereotype.Service;

@Service
public class LabelServiceBean implements LabelService {

    public String findByCode(String code) {
        return "[LABEL(" + code + ")]";
    }
}
