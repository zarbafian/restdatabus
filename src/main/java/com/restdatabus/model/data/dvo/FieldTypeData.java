package com.restdatabus.model.data.dvo;

public class FieldTypeData {

    private String key;

    private String label;

    public FieldTypeData() {
    }

    @Override
    public String toString() {
        return "FieldTypeData{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    public FieldTypeData(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
