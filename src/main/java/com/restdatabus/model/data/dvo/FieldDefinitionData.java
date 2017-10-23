package com.restdatabus.model.data.dvo;

import java.io.Serializable;

public class FieldDefinitionData implements Serializable {

    private String name;

    private FieldTypeData fieldType;

    public FieldDefinitionData() {
    }

    public FieldDefinitionData(String name, FieldTypeData fieldType) {
        this.name = name;
        this.fieldType = fieldType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldTypeData getFieldType() {
        return fieldType;
    }

    public void setType(FieldTypeData fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return "FieldDefinitionData{" +
                "name='" + name + '\'' +
                ", fieldType='" + fieldType + '\'' +
                '}';
    }
}
