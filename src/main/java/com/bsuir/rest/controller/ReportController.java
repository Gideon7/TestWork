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
    public List<ReportForm> getReport(@RequestParam("userId") Long userId) {
        return reportService.getReport(userId);
    }
}
