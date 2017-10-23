package com.restdatabus.model.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * A persistent model of a business entity type.
 */
public class EntityDefinition {

    /**
     * The unique identifier of this entity type.
     */
    private Long id;

    /**
     * The name of this type of entity.
     */
    private String name;

    /**
     * The definitions of the fields;
     */
    private List<FieldDefinition> definitions;

    /**
     * Initializing constructor.
     * @param name the name of this entity type.
     */
    public EntityDefinition(String name) {
        this.definitions = new ArrayList<>();
        this.name = name;
    }

    @Override
    public String toString() {
        return "EntityDefinition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", definitions=" + definitions +
                '}';
    }

    public List<FieldDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<FieldDefinition> definitions) {
        this.definitions = definitions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityDefinition(EntityDefinition entityDefinition) {
        this.id = entityDefinition.getId();
        this.name = entityDefinition.getName();
        this.definitions = new ArrayList<>(entityDefinition.getDefinitions().size());
        for(FieldDefinition fieldDefinition: entityDefinition.getDefinitions()) {
            this.definitions.add(new FieldDefinition(fieldDefinition));
        }
    }
}
