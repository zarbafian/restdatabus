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

    /**
     * The identifier of the entity definition to which the field pertains.
     */
    private Long entityDefinitionId;

    public FieldDefinition(String name, DataType type) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "FieldDefinition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", entityDefinitionId=" + entityDefinitionId +
                '}';
    }

    public abstract FieldDefinition clone();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public Long getEntityDefinitionId() {
        return entityDefinitionId;
    }

    public void setEntityDefinitionId(Long entityDefinitionId) {
        this.entityDefinitionId = entityDefinitionId;
    }
}
