package com.restdatabus.model.data.transform;

import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import com.restdatabus.model.data.dvo.FieldTypeData;
import com.restdatabus.model.meta.*;
import com.restdatabus.model.service.EntityDefinitionService;
import com.restdatabus.model.service.FieldTypeService;
import com.restdatabus.model.service.LabelService;
import com.restdatabus.web.api.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class in charge of building data value objects from persisted entities.
 */
@Service
public class EntityDefinitionObjectMapper {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionObjectMapper.class);

    @Autowired
    private FieldTypeService fieldTypeService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private EntityDefinitionService entityDefinitionService;

    private EntityDefinitionObjectMapper(){}

    public EntityDefinitionData toDataObject(EntityDefinition entityDefinition) {

        LOG.debug("toDataObject: {}", entityDefinition);

        EntityDefinitionData data = new EntityDefinitionData();

        data.setName(entityDefinition.getName());

        for(FieldDefinition fieldDefinition: entityDefinition.getDefinitions()) {
            data.getFields().add(
                    this.toDataObject(fieldDefinition)
            );
        }

        return data;
    }

    public FieldDefinitionData toDataObject(FieldDefinition fieldDefinition) {

        LOG.debug("toDataObject: {}", fieldDefinition);

        FieldType fieldType = fieldTypeService.findById(fieldDefinition.getFieldTypeId());

        FieldDefinitionData data = new FieldDefinitionData();
        data.setName(fieldDefinition.getName());
        data.setType(
                new FieldTypeData(
                        fieldType.getKey(),
                        labelService.findByCode(fieldType.getKey())
                )
        );
        if(Constants.FIELD_TYPE_ENTITY.equals(fieldType.getKey())) {
            data.setTargetEntity(
                    entityDefinitionService.findById(fieldDefinition.getTargetEntityId()).getName()
            );
        }

        return data;
    }

    public EntityDefinition toEntityObject(EntityDefinitionData data) {

        LOG.debug("toEntityObject: {}", data);

        EntityDefinition entityDefinition = new EntityDefinition(data.getName());
        for(FieldDefinitionData fData: data.getFields()) {
            entityDefinition.getDefinitions().add(toEntityObject(fData));
        }

        return entityDefinition;
    }

    public FieldDefinition toEntityObject(FieldDefinitionData data) {

        LOG.debug("toEntityObject: {}", data);

        FieldType fieldType = fieldTypeService.findByKey(data.getFieldType().getKey());

        FieldDefinition definition = new FieldDefinition();
        definition.setName(data.getName());
        definition.setFieldTypeId(fieldType.getId());
        if(Constants.FIELD_TYPE_ENTITY.equals(fieldType.getKey())) {
            definition.setTargetEntityId(
                    entityDefinitionService.findByName(data.getTargetEntity()).getId()
            );
        }

        return definition;
    }

    public FieldTypeData toDataObject(FieldType fieldType) {

        LOG.debug("toDataObject: {}", fieldType);

        FieldTypeData data = new FieldTypeData();
        data.setKey(fieldType.getKey());
        data.setLabel(labelService.findByCode(fieldType.getKey()));

        return data;
    }
}
