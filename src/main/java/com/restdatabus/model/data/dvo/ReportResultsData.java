package com.restdatabus.model.data.dvo;

import com.restdatabus.model.data.dvo.table.ColumnData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportResultsData {

    private List<ColumnData> columns;
    private List<Map<String, Object>> rows;

    public ReportResultsData() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public List<ColumnData> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return "ReportResultsData{" +
                "columns=" + columns +
                ", rows=" + rows +
                '}';
    }

    public void setColumns(List<ColumnData> columns) {
        this.columns = columns;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}
