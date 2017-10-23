package com.restdatabus.model.meta;

public class FieldType {

    private Long id;

    private String key;

    public FieldType() {
    }

    public FieldType(Long id, String key) {
        this.id = id;
        this.key = key;
    }

    public FieldType(FieldType fieldType) {
        this.id = fieldType.getId();
        this.key = fieldType.getKey();
    }

    @Override
    public String toString() {
        return "FieldType{" +
                "id=" + id +
                ", key='" + key + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
