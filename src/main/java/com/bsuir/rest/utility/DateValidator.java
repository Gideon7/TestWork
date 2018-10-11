package com.bsuir.rest.utility;

import com.bsuir.rest.exception.UnprocessableEntityException;

import java.time.LocalDate;

public class DateValidator {
    public static void validateDateString(String date) {

        try {
            LocalDate.parse(date);
        } catch(RuntimeException e) {
            throw new UnprocessableEntityException();
        }
    }
}
