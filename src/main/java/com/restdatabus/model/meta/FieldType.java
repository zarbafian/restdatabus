package com.restdatabus.model.meta;

public class FieldType {

    private Long id;

    private String key;

    private String sqlType;

    public FieldType() {
    }

    public FieldType(FieldType fieldType) {
        this.id = fieldType.getId();
        this.key = fieldType.getKey();
        this.sqlType = fieldType.getSqlType();
    }

    @Override
    public String toString() {
        return "FieldType{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", sqlType='" + sqlType + '\'' +
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

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }
}
