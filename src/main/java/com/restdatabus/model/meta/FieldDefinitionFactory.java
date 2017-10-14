package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

public class FieldDefinitionFactory {

    private FieldDefinitionFactory(){}

    public static FieldDefinition buildFromKey(String key, String name) {

        DataType type = DataType.fromKey(key);

        switch (type) {
            case INTEGER:
                return new IntegerFieldDefinition(name);
            case YESNO:
                return new YesNoFieldDefinition(name);
            case TEXT:
                return new TextFieldDefinition(name);
            case DECIMAL:
                return new DecimalFieldDefinition(name);
            default:
                throw new IllegalArgumentException("field type '" + key + "' does not exist");
        }
    }
}
