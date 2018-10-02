package com.bsuir.rest.entity;

import com.bsuir.rest.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "hash_password")
    private String hashPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private List<JogInfoEntity> jogInfoEntityList;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private List<TokenEntity> tokenEntityList;
}
