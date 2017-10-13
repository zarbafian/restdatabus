package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.types.TextDataType;

public class IntegerFieldDefinition extends FieldDefinition<TextDataType> {

    public IntegerFieldDefinition(String name) {
        super(name, DataType.INTEGER);
    }
}
