package com.restdatabus.model.meta;

/**
 * The definition of a field.
 */
public class FieldDefinition {

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
    private Long fieldTypeId;

    /**
     * The identifier of the entity definition to which the field pertains.
     */
    private Long entityDefinitionId;

    /**
     * The identifier of the entity definition that is the target of this relation.
     */
    private Long targetEntityId;

    public FieldDefinition() {
    }

    public FieldDefinition(FieldDefinition definition) {
        this.id = definition.getId();
        this.name = definition.getName();
        this.fieldTypeId = definition.getFieldTypeId();
        this.entityDefinitionId = definition.getEntityDefinitionId();
        this.targetEntityId = definition.getTargetEntityId();
    }

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

    public Long getFieldTypeId() {
        return fieldTypeId;
    }

    public void setFieldTypeId(Long fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    public Long getEntityDefinitionId() {
        return entityDefinitionId;
    }

    public void setEntityDefinitionId(Long entityDefinitionId) {
        this.entityDefinitionId = entityDefinitionId;
    }

    @Override
    public String toString() {
        return "FieldDefinition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fieldTypeId=" + fieldTypeId +
                ", entityDefinitionId=" + entityDefinitionId +
                ", targetEntityId=" + targetEntityId +
                '}';
    }

    public Long getTargetEntityId() {
        return targetEntityId;
    }

    public void setTargetEntityId(Long targetEntityId) {
        this.targetEntityId = targetEntityId;
    }
}
