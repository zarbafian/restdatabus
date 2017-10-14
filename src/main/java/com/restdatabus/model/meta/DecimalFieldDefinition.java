package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

public class DecimalFieldDefinition extends FieldDefinition
{

    public DecimalFieldDefinition(String name) {
        super(name, DataType.DECIMAL);
    }

}
