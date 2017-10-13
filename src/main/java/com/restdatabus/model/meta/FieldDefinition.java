package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;

/**
 * The definition of a field.
 * @param <T> the data type associated with this definition.
 */
public abstract class FieldDefinition<T> {

    /**
     * The unique identifier of this field definition.
     */
    private Long id;

    /**
     * The name of the field.
     */
    private String name;

    /**
     * The type of the field.
     */
    private DataType type;


    public FieldDefinition(String name, DataType type) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "FieldDefinition{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
