package com.bsuir.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogInfoForm {
    private Double distance;
    private String time;
    private String date;
}
