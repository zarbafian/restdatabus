package com.restdatabus.model.data.dvo;

import java.io.Serializable;

public class FieldDefinitionData implements Serializable {

    private String name;

    private String type;

    public FieldDefinitionData(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldDefinitionData that = (FieldDefinitionData) o;

        if (!name.equals(that.name)) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FieldDefinitionData{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
