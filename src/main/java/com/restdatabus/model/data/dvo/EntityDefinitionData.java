package com.restdatabus.model.data.dvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntityDefinitionData implements Serializable {

    private String name;

    private List<FieldDefinitionData> fields;

    public EntityDefinitionData() {
        this.fields = new ArrayList<>();
    }

    public EntityDefinitionData(String name) {
        this();
        this.name = name;
    }


    @Override
    public String toString() {
        return "EntityDefinitionData{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityDefinitionData that = (EntityDefinitionData) o;

        if (!name.equals(that.name)) return false;
        return fields.equals(that.fields);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + fields.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldDefinitionData> getFields() {
        return fields;
    }

    public void setFields(List<FieldDefinitionData> fields) {
        this.fields = fields;
    }
}
