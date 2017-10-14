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

        FieldDefinition fieldDefinition;

        DataType type = DataType.fromKey(data.getType());

        switch (type) {
            case INTEGER:
                return new IntegerFieldDefinition(data.getName());
            case YESNO:
                return new YesNoFieldDefinition(data.getName());
            case TEXT:
                return new TextFieldDefinition(data.getName());
                default:
                    throw new NotImplementedException();
        }
    }
}
