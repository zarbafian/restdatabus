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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityDefinition that = (EntityDefinition) o;

        if (!id.equals(that.id)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public List<FieldDefinition> getDefinitions() {
        return definitions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
