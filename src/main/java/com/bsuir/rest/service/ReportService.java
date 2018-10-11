package com.bsuir.rest.service;

import com.bsuir.rest.model.ReportForm;

import java.util.List;

public interface ReportService {
    List<ReportForm> getReport(int page, int pageSize, Long userId);
}
