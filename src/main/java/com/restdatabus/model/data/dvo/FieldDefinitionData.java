package com.restdatabus.model.data.dvo;

import java.io.Serializable;

public class FieldDefinitionData implements Serializable {

    private String name;

    private FieldTypeData fieldType;

    private String targetEntity;

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

    public void setFieldType(FieldTypeData fieldType) {
        this.fieldType = fieldType;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    @Override
    public String toString() {
        return "FieldDefinitionData{" +
                "name='" + name + '\'' +
                ", fieldType=" + fieldType +
                ", targetEntity='" + targetEntity + '\'' +
                '}';
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

}
