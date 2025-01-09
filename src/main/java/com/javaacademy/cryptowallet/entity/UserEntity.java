package com.javaacademy.cryptowallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserEntity {
    private String login;
    private String email;
    private String password;
}
