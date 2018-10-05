package com.bsuir.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@ToString(exclude = "userEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jogs_info")
public class JogInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "distance")
    private double distance;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "date")
    private LocalDate date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public JogInfoEntity(double distance, LocalTime time, LocalDate date, UserEntity userEntity) {
        this.distance = distance;
        this.time = time;
        this.date = date;
        this.userEntity = userEntity;
    }
}
