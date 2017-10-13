package com.restdatabus.model.data.types;

import com.restdatabus.model.data.Field;

public class TextDataType extends Field<String> {

    /**
     * Default constructor.
     */
    public TextDataType() {
        this.value = null;
    }

    /**
     * Initializing constructor.
     * @param value the value of the field.
     */
    public TextDataType(String value) {
        super(value);
    }
}
