package com.javaacademy.cryptowallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Дто для смены пароля")
public class ResetPasswordDto {

    @Schema(description = "Логин пользователя")
    @NonNull
    private String login;

    @Schema(description = "Старый пароль")
    @NonNull
    @JsonProperty("old_password")
    private String oldPassword;

    @Schema(description = "Новый пароль")
    @NonNull
    @JsonProperty("new_password")
    private String newPassword;
}
