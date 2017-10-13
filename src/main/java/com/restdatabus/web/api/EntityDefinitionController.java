package com.restdatabus.web.api;

import com.restdatabus.business.api.EntityDefinitionManager;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class EntityDefinitionController {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionController.class);

    @Autowired
    private EntityDefinitionManager manager;

    @RequestMapping(
            value = "/definitions",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EntityDefinitionData create(@RequestBody EntityDefinitionData data) {

        LOG.debug("create: {}", data);

        EntityDefinitionData persistedData = manager.create(data);

        return persistedData;
    }
}
