package com.bsuir.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportForm {
    private String week;
    private String averageSpeed;
    private String averageTime;
    private String totalDistance;
}
