package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

public class IntegerFieldDefinition extends FieldDefinition {

    public IntegerFieldDefinition(String name) {
        super(name, DataType.INTEGER);
    }

}
