package com.restdatabus.model.data.transform;

import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import com.restdatabus.model.data.dvo.FieldTypeData;
import com.restdatabus.model.meta.*;
import com.restdatabus.model.service.FieldTypeService;
import com.restdatabus.model.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class in charge of building data value objects from persisted entities.
 */
@Service
public class EntityDefinitionObjectMapper {

    @Autowired
    private FieldTypeService fieldTypeService;

    @Autowired
    private LabelService labelService;

    private EntityDefinitionObjectMapper(){}

    public EntityDefinitionData toDataObject(EntityDefinition entityDefinition) {

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

        FieldType fieldType = fieldTypeService.findById(fieldDefinition.getFieldTypeId());

        FieldDefinitionData data = new FieldDefinitionData();
        data.setName(fieldDefinition.getName());
        data.setType(
                new FieldTypeData(
                        fieldType.getKey(),
                        labelService.findByCode(fieldType.getKey())
                )
        );

        return data;
    }

    public EntityDefinition toEntityObject(EntityDefinitionData data) {

        EntityDefinition entityDefinition = new EntityDefinition(data.getName());
        for(FieldDefinitionData fData: data.getFields()) {
            entityDefinition.getDefinitions().add(toEntityObject(fData));
        }

        return entityDefinition;
    }

    public FieldDefinition toEntityObject(FieldDefinitionData data) {

        FieldType fieldType = fieldTypeService.findByKey(data.getFieldType().getKey());

        FieldDefinition definition = new FieldDefinition();
        definition.setName(data.getName());
        definition.setFieldTypeId(fieldType.getId());

        return definition;
    }

    public FieldTypeData toDataObject(FieldType fieldType) {

        FieldTypeData data = new FieldTypeData();
        data.setKey(fieldType.getKey());
        data.setLabel(labelService.findByCode(fieldType.getKey()));

        return data;
    }
}
