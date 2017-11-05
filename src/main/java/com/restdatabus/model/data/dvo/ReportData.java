package com.restdatabus.model.data.dvo;

public class ReportData {

    private String name;

    public ReportData(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
