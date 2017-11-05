package com.restdatabus.business.api;

import com.restdatabus.model.data.dvo.ReportData;
import com.restdatabus.model.data.dvo.ReportResultsData;

import java.util.List;

public interface ReportManager {

    List<ReportData> getEntityReports();

    ReportResultsData getEntityReport(String type);
}
