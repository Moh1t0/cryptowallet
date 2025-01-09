package com.javaacademy.cryptowallet.controller;


import com.javaacademy.cryptowallet.dto.ExceptionDto;
import com.javaacademy.cryptowallet.dto.ResetPasswordDto;
import com.javaacademy.cryptowallet.dto.UserDto;
import com.javaacademy.cryptowallet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "Контроллер для работы с пользователем", description = "Возможность создать пользователя"
        + " а также сменить пароль")
public class UserController {
    private final UserService service;
    private static final int CREATED_STATUS = 201;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрация и сохранение нового пользователя")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Пользователь зарегистрирован"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Логин занят",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    })
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> signup(@RequestBody UserDto user) {
        service.saveNewUser(user);
        return ResponseEntity.status(CREATED_STATUS).body(user);
    }


    @Operation(summary = "Смена пароля пользователя",
            description = "Изменение пароля пользователя")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Пароль изменен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный пароль",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    })
    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        service.resetPassword(resetPasswordDto.getLogin(),
                resetPasswordDto.getOldPassword(),
                resetPasswordDto.getNewPassword());
        return ResponseEntity.status(200).body(resetPasswordDto);
    }
}
