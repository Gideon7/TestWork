package com.bsuir.rest.model;

import com.bsuir.rest.entity.JogInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JogInfoForm {
    private Double distance;
    private String time;
    private String date;

    public static List<JogInfoForm> from(List<JogInfoEntity> entityList) {
        List<JogInfoForm> formList = new ArrayList<>();

        entityList.forEach(entity -> {
            formList.add(JogInfoForm.builder()
                            .distance(entity.getDistance())
                            .time(entity.getTime().toString())
                            .date(entity.getDate().toString())
                            .build());
        });

        return formList;
    }

    public static JogInfoForm from(JogInfoEntity entity) {
        return JogInfoForm.builder()
                .distance(entity.getDistance())
                .time(entity.getTime().toString())
                .date(entity.getDate().toString())
                .build();
    }
}
