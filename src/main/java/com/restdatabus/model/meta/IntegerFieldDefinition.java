package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.types.TextDataType;

public class IntegerFieldDefinition extends FieldDefinition<TextDataType> {

    public IntegerFieldDefinition(String name) {
        super(name, DataType.INTEGER);
    }

    @Override
    public FieldDefinition clone() {
        FieldDefinition field = new IntegerFieldDefinition(this.getName());
        field.setId(this.getId());
        field.setType(this.getType());
        field.setEntityDefinitionId(this.getEntityDefinitionId());
        return field;
    }
}
