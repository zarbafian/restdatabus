package com.restdatabus.model.data;

import com.restdatabus.model.meta.FieldDefinition;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    /**
     * Identifier of the entity's definition
     */
    private Long definitionId;

    /**
     * Identifier of the entity
     */
    private Long id;

    /**
     * Fields of the entity
     */
    private List<Field> fields;

    public Entity() {
        this.fields = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Entity{" +
                "definitionId=" + definitionId +
                ", id=" + id +
                ", fields=" + fields +
                '}';
    }

    public Long getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(Long definitionId) {
        this.definitionId = definitionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Field getField(FieldDefinition fieldDefinition) {

        for(Field f: fields) {
            if ((f.getId().equals(fieldDefinition.getId()))) {
                return f;
            }
        }
        return null;
    }
}
