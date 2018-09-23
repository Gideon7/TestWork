package com.bsuir.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.context.annotation.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@ToString(exclude = "userInfoList, tokenList")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "hash_password")
    private String hashPassword;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserInfo> userInfoList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokenList;
}
