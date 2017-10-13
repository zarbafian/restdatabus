package com.restdatabus.model.data;

import com.restdatabus.model.meta.EntityDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * A business entity.
 */
public class Entity {

    /**
     * The type of this entity.
     */
    private EntityDefinition entityDefinition;

    /**
     * The unique identifier of this entity type.
     */
    private Long id;

    /**
     * The list of data fields associated with this type of entity.
     */
    private Map<String, Field> fields;

    /**
     * Default constructor.
     */
    public Entity() {

        this.fields = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (!entityDefinition.getId().equals(entity.entityDefinition.getId())) return false;
        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        int result = entityDefinition.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    public Map<String, Field> getFields() {
        return fields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
