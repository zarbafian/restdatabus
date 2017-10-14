package com.restdatabus.model.data.transform;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import com.restdatabus.model.meta.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class EntityDefinitionHelper {

    public static EntityDefinitionData persistToDvo(EntityDefinition entityDefinition) {

        EntityDefinitionData data = new EntityDefinitionData();

        data.setName(entityDefinition.getName());
        for(FieldDefinition field: entityDefinition.getDefinitions()) {
            data.getFields().add( persistToDvo(field) );
        }

        return data;
    }

    public static FieldDefinitionData persistToDvo(FieldDefinition field) {

        FieldDefinitionData data = new FieldDefinitionData();
        data.setName(field.getName());
        data.setType(field.getType().getKey());

        return data;
    }

    public static EntityDefinition dvoToPersist(EntityDefinitionData data) {

        EntityDefinition entityDefinition = new EntityDefinition(data.getName());

        for(FieldDefinitionData fData: data.getFields()) {

            entityDefinition.getDefinitions().add( dvoToPersist(fData) );
        }

        return entityDefinition;
    }

    public static FieldDefinition dvoToPersist(FieldDefinitionData data) {

        FieldDefinition fieldDefinition = FieldDefinitionFactory.buildFromKey(data.getType(),data.getName());

        return fieldDefinition;
    }
}
