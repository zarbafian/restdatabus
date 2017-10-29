package com.restdatabus.model.data.dvo;

public class FieldDefinitionData {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldDefinitionData that = (FieldDefinitionData) o;

        if (!name.equals(that.name)) return false;
        if (!fieldType.equals(that.fieldType)) return false;
        return targetEntity != null ? targetEntity.equals(that.targetEntity) : that.targetEntity == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + fieldType.hashCode();
        result = 31 * result + (targetEntity != null ? targetEntity.hashCode() : 0);
        return result;
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
