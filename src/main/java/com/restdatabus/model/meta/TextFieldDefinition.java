package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

public class TextFieldDefinition extends FieldDefinition {

    public TextFieldDefinition(String name) {
        super(name, DataType.TEXT);
    }

}
