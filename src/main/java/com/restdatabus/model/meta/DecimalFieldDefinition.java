package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

import java.math.BigDecimal;

public class DecimalFieldDefinition extends FieldDefinition<BigDecimal> {

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
