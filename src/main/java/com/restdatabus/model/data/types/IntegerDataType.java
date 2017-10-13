package com.restdatabus.model.data.types;

import com.restdatabus.model.data.Field;

public class IntegerDataType extends Field<Integer> {

    /**
     * Default constructor.
     */
    public IntegerDataType() {
        this.value = null;
    }

    /**
     * Initializing constructor.
     * @param value the value of the field.
     */
    public IntegerDataType(Integer value) {
        super(value);
    }
}
