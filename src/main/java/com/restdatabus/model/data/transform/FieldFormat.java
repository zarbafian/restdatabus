package com.restdatabus.model.data.transform;

import com.restdatabus.common.DateFormat;
import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;

public class FieldFormat {

    private FieldFormat() {}

    public static Field setValue(Field field, Object value) {

        if(DataType.ENTITY.equals(field.getDataType())) {

            // Change integer to long for entities
            if(value instanceof Integer) {
                field.setValue(((Integer)value).longValue());
            }
        }
        else if(DataType.DATE.equals(field.getDataType())) {

            // Cast string to date
            if(value instanceof String) {
                field.setValue(DateFormat.parseDate((String) value));
            }
            else {
                throw new IllegalArgumentException("string expected for date field: " + field.toString());
            }
        }
        else if(DataType.DATETIME.equals(field.getDataType())) {

            // Cast string to date
            if(value instanceof String) {
                field.setValue(DateFormat.parseDatetime((String) value));
            }
            else {
                throw new IllegalArgumentException("string expected for datetime field: " + field.toString());
            }
        }
        else if(DataType.DECIMAL.equals(field.getDataType())) {

            if(value instanceof Number) {
                field.setValue(((Number) value).doubleValue());
            }
        }
        else {
            field.setValue(value);
        }

        return field;
    }
}
