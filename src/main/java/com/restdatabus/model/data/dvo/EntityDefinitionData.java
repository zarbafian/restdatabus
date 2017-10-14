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

    @Override
    public String toString() {
        return "EntityDefinitionData{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
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
