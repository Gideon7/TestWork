package com.bsuir.rest.model;

import com.bsuir.rest.entity.UserEntity;
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
public class User {
    private Long id;
    private String username;
    private Role role;

    public static List<User> from(List<UserEntity> entityList) {
        List<User> userList = new ArrayList<>();

        entityList.forEach(entity -> {
            userList.add(User.builder()
                        .id(entity.getId())
                        .username(entity.getUsername())
                        .role(entity.getRole())
                        .build());
        });

        return userList;
    }

    public static User from(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .role(entity.getRole())
                .build();
    }
}
