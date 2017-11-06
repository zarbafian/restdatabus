package com.restdatabus.model.data.transform;

import com.restdatabus.common.DateFormat;
import com.restdatabus.events.EventLog;
import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Entity;
import com.restdatabus.model.data.Field;
import com.restdatabus.model.data.dvo.EntityData;
import com.restdatabus.model.data.dvo.EventLogData;
import com.restdatabus.model.data.types.FieldFactory;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import com.restdatabus.model.meta.FieldType;
import com.restdatabus.model.service.FieldTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityObjectMapper {

    private static final Logger LOG = LoggerFactory.getLogger(EntityObjectMapper.class);

    @Autowired
    private FieldTypeService fieldTypeService;

    public Entity toEntityObject(EntityData data, EntityDefinition entityDefinition) {

        LOG.debug("> toEntityObject: {} -> {}", data, entityDefinition);

        // Convert field names to field definition identifiers
        List<Field> fields = new ArrayList<>();

        data.getData().forEach( (key, value) -> {

            FieldDefinition fieldDefinition = entityDefinition.findFieldByName(key);

            FieldType fieldType = fieldTypeService.findById(fieldDefinition.getFieldTypeId());

            Field field = FieldFactory.build(fieldType);

            field.setId(fieldDefinition.getId());

            FieldFormat.setValue(field, value);

            fields.add(field);
        });

        Entity entity = new Entity();
        entity.setDefinitionId(entityDefinition.getId());
        entity.setId(data.getId());
        entity.setFields(fields);

        LOG.debug("< toEntityObject: {}", entity);

        return entity;
    }

    public EntityData toDataObject(Entity entity, EntityDefinition definition) {

        LOG.debug("> toDataObject: {} -> {}", entity, definition);

        Map<String, Object> fields = new HashMap<>();

        definition.getDefinitions().forEach(fieldDefinition -> {

            Field field = entity.getField(fieldDefinition);
            if(field != null) {
                fields.put(fieldDefinition.getName(), field.getValue());
            }
            else {
                fields.put(fieldDefinition.getName(), null);
            }
        });

        EntityData data = new EntityData();
        data.setId(entity.getId());
        data.setType(definition.getName());
        data.setData(fields);

        LOG.debug("< toDataObject: {}", data);

        return data;
    }

    public EventLogData toDataObject(EventLog eventLog) {

        EventLogData data = new EventLogData();
        data.setId(eventLog.getId());
        data.setAction(eventLog.getType().getValue());
        data.setTimestamp(DateFormat.formatDatetime(eventLog.getTimestamp()));
        data.setTarget(eventLog.getPaths().toString());
        data.setParams(eventLog.getParams().toString());

        return data;
    }
}
