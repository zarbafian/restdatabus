package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

/**
 * A field having only two possible values: yes and no.
 */
public class YesNoFieldDefinition extends FieldDefinition {

    public YesNoFieldDefinition(String name) {
        super(name, DataType.YESNO);
    }

    @Override
    public FieldDefinition clone() {
        FieldDefinition field = new YesNoFieldDefinition(this.getName());
        field.setId(this.getId());
        field.setType(this.getType());
        field.setEntityDefinitionId(this.getEntityDefinitionId());
        return field;
    }
}
