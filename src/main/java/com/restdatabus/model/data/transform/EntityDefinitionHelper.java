package com.restdatabus.model.data.transform;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.dvo.EntityDefinitionData;
import com.restdatabus.model.data.dvo.FieldDefinitionData;
import com.restdatabus.model.meta.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class EntityDefinitionHelper {

    public static EntityDefinition dvoToPersist(EntityDefinitionData data) {

        EntityDefinition entityDefinition = new EntityDefinition(data.getName());

        for(FieldDefinitionData fData: data.getFields()) {

            entityDefinition.getDefinitions().add( dvoToPersist(fData) );
        }

        return entityDefinition;
    }

    public static FieldDefinition dvoToPersist(FieldDefinitionData data) {

        FieldDefinition fieldDefinition;

        DataType type = DataType.parse(data.getType());
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
