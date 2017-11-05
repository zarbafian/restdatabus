package com.restdatabus.business.api.impl;

import com.restdatabus.model.data.Entity;
import com.restdatabus.model.data.Field;
import com.restdatabus.model.data.dvo.ReportData;
import com.restdatabus.model.data.dvo.ReportResultsData;
import com.restdatabus.model.data.dvo.table.ColumnData;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import com.restdatabus.model.service.EntityDefinitionService;
import com.restdatabus.model.service.EntityService;
import com.restdatabus.web.api.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class InternalReportManagerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(InternalReportManagerImpl.class);

    @Autowired
    private EntityDefinitionService entityDefinitionService;

    @Autowired
    private EntityService entityService;

    public List<ReportData> getEntityReports() {

        LOG.debug("getEntityReports");

        List<EntityDefinition> entityDefinitions = entityDefinitionService.findAll();

        List<ReportData> reportDatas = new ArrayList<>();
        for (EntityDefinition entityDefinition: entityDefinitions) {

            reportDatas.add(new ReportData(entityDefinition.getName()));
        }

        return reportDatas;
    }

    public ReportResultsData getEntityReport(String type) {

        LOG.debug("> getEntityReport: {}", type);

        EntityDefinition entityDefinition = entityDefinitionService.findByName(type);

        // Check type
        if(entityDefinition == null) {
            throw new IllegalArgumentException("entity not found: " + type);
        }

        List<Entity> entities = entityService.findByDefinition(entityDefinition);

        ReportResultsData data = new ReportResultsData();

        data.getColumns().add(new ColumnData(Constants.ID, Constants.ID));

        for (FieldDefinition fd: entityDefinition.getDefinitions()) {

            data.getColumns().add(new ColumnData(fd.getName(), fd.getName()));
        }

        for (Entity e: entities) {
            Map<String, Object> row = new HashMap<>();
            row.put(Constants.ID, e.getId());
            for (Field f: e.getFields()) {
                String fieldName = entityDefinition.findFieldById(f.getId()).getName();
                row.put(fieldName, f.getValue());
            }
            data.getRows().add(row);
        }

        LOG.debug("< getEntityReport: {} -> {}", type, data);

        return data;
    }
}
