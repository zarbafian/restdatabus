package com.restdatabus.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class EntityController {

    private static final Logger LOG = LoggerFactory.getLogger(EntityController.class);

    @RequestMapping(
            value = "/entities",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object create(@PathVariable(value = "entity") String entity, @RequestBody Map<String, Object> payload) {

        LOG.debug("entity: {}", entity);
        LOG.debug("payload: {}", payload);

        return null;
    }
}
