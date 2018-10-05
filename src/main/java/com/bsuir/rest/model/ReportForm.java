package com.bsuir.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportForm {
    private int reportFormId;
    private int weekOfYear;
    private String firstDayOfWeek;
    private String lastDayOfWeek;
    private BigDecimal averageSpeed;
    private String averageTime;
    private BigDecimal totalDistance;
}
