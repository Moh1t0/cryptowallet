package com.javaacademy.cryptowallet.entity;

import lombok.Data;

@Data
public class UserEntity {
    private String login;
    private String email;
    private String password;
}
