package com.bsuir.rest.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogInfoForm {
    private double distance;
    private String time;
    private String date;
}
