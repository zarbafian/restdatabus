package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

public class DecimalFieldDefinition extends FieldDefinition
{

    public DecimalFieldDefinition(String name) {
        super(name, DataType.DECIMAL);
    }

    @Override
    public FieldDefinition clone() {
        FieldDefinition field = new DecimalFieldDefinition(this.getName());
        field.setId(this.getId());
        field.setType(this.getType());
        field.setEntityDefinitionId(this.getEntityDefinitionId());
        return field;
    }
}
