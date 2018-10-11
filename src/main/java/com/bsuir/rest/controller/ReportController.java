package com.bsuir.rest.controller;

import com.bsuir.rest.model.ReportForm;
import com.bsuir.rest.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/report")
    public List<ReportForm> getReport(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                      @RequestParam("userId") Long userId) {
        return reportService.getReport(page, pageSize, userId);
    }
}
