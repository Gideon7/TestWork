package com.bsuir.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@ToString(exclude = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jogs_info")
public class JogInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "distance")
    private double distance;

    @Column(name = "time")
    private String time;

    @Column(name = "date")
    private String date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
