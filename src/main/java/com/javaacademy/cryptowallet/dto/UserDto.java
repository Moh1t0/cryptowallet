package com.javaacademy.cryptowallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Дто для создания пользователя")
public class UserDto {

    @Schema(description = "Уникальный логин")
    private String login;

    @Schema(description = "Почта пользователя")
    private String email;

    @Schema(description = "Пароль пользователя")
    private String password;
}
