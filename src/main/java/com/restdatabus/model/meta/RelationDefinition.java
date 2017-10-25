package com.restdatabus.model.meta;

public class RelationDefinition {

    /**
     * Unique id of this relation between a field and another entity definition.
     */
    private Long id;

    /**
     * Id of source field definition.
     */
    private Long originField;

    /**
     * Id of target entity definition
     */
    private Long targetEntity;

    public RelationDefinition(Long id, Long originField, Long targetEntity) {
        this.id = id;
        this.originField = originField;
        this.targetEntity = targetEntity;
    }

    public RelationDefinition(RelationDefinition relationDefinition) {
        this.id = relationDefinition.getId();
        this.originField = relationDefinition.getOriginField();
        this.targetEntity = relationDefinition.getTargetEntity();
    }

    @Override
    public String toString() {
        return "RelationDefinition{" +
                "id=" + id +
                ", originField=" + originField +
                ", targetEntity=" + targetEntity +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginField() {
        return originField;
    }

    public void setOriginField(Long originField) {
        this.originField = originField;
    }

    public Long getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(Long targetEntity) {
        this.targetEntity = targetEntity;
    }
}
