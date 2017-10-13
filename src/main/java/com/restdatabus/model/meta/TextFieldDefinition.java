package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.types.TextDataType;

public class TextFieldDefinition extends FieldDefinition<TextDataType> {

    public TextFieldDefinition(String name) {
        super(name, DataType.TEXT);
    }
}
