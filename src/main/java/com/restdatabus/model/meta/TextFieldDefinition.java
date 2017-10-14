package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

public class TextFieldDefinition extends FieldDefinition {

    public TextFieldDefinition(String name) {
        super(name, DataType.TEXT);
    }

    @Override
    public FieldDefinition clone() {
        FieldDefinition field = new TextFieldDefinition(this.getName());
        field.setId(this.getId());
        field.setType(this.getType());
        field.setEntityDefinitionId(this.getEntityDefinitionId());
        return field;
    }
}
