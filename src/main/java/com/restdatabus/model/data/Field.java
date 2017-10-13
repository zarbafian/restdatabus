package com.restdatabus.model.data;

/**
 * Base class for entity fields.
 * @param <T> the type of the data contained
 */
public abstract class Field<T> {

    /**
     * The value of the field.
     */
    protected T value;

    /**
     * Default constructor.
     */
    public Field() {
    }

    /**
     * Initializing constructor.
     * @param value the value of the field.
     */
    public Field(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Field{" +
                "value=" + value +
                '}';
    }
}
