package com.restdatabus.model.data.dvo;

import java.util.Map;

public class EntityData {

    private Long id;

    private String type;

    private Map<String, Object> data;

    @Override
    public String toString() {
        return "EntityData{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", data=" + data +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
