package com.restdatabus.model.data.dvo.table;

public class ColumnData {

    private String name;
    private String property;

    public ColumnData(String name, String property) {
        this.name = name;
        this.property = property;
    }

    @Override
    public String toString() {
        return "ColumnData{" +
                "name='" + name + '\'' +
                ", property='" + property + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
