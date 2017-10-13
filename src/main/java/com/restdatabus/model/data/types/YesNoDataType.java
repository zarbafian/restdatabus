package com.restdatabus.model.data.types;

import com.restdatabus.model.data.Field;

public class YesNoDataType extends Field<Boolean> {

    /**
     * Default constructor.
     */
    public YesNoDataType() {
        this.value = null;
    }

    /**
     * Initializing constructor.
     * @param value the value of the field.
     */
    public YesNoDataType(Boolean value) {
        super(value);
    }
}
