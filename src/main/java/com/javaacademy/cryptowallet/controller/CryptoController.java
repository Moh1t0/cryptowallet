package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.AccountReplenishmentDto;
import com.javaacademy.cryptowallet.dto.CryptoAccountDto;
import com.javaacademy.cryptowallet.dto.CryptoCreateDto;
import com.javaacademy.cryptowallet.dto.ExceptionDto;
import com.javaacademy.cryptowallet.entity.CryptoAccountEntity;
import com.javaacademy.cryptowallet.service.CryptoAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptowallet")
@Tag(name = "Контроллер для работы с крипто кошельками",
        description = "Создание счетов, изменение баланса, перевода криптовалюты")
public class CryptoController {
    private final CryptoAccountService service;


    @Operation(
            summary = "Создание криптосчета",
            description = "Создание нового криптокошелька в определенной криптовалюте")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Криптосчет создан"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> createAccount(@RequestBody CryptoCreateDto accountDto) {
        CryptoAccountEntity account = service.createAccount(accountDto);
        return ResponseEntity.ok(account.getId());
    }


    @Operation(
            summary = "Получение всех криптосчетов пользователя")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Криптосчета не найдены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    }
    )
    @GetMapping
    public ResponseEntity<List<CryptoAccountDto>> getAll(@RequestParam String username) {
        try {
            List<CryptoAccountDto> accounts = service.getAllAccount(username);
            return ResponseEntity.ok(accounts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        }
    }


    @Operation(
            summary = "Пополнение баланса в рублях")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Успешное пополнение криптокошелька"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Криптокошелек не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    }
    )
    @PostMapping("/refill")
    public void replenishesAccount(@RequestBody AccountReplenishmentDto accountReplenishmentDto) {
        service.buyCryptoForRubles(accountReplenishmentDto);
    }


    @Operation(
            summary = "Списание рублей с криптокошелька")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Успешное списание"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Криптокошелек не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    }
    )
    @PostMapping("/withdrawal")
    public void withdrawsRublesFromAccount(@RequestBody AccountReplenishmentDto account) {
        service.sellCryptoForRubles(account);
    }


    @Operation(
            summary = "Получение баланса в рублях")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Баланс кошелька в рублях"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Криптокошелек не найден!",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    }
    )
    @GetMapping("/balance/{id}")
    public BigDecimal getRubleEquivalentCryptoAccount(@PathVariable UUID id) {
        return service.getAccountBalanceInRubles(id);
    }


    @Operation(
            summary = "Получение баланса в рублях со ВСЕХ криптосчетов")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Баланс всех криптосчетов в рублях"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Счета не найдены!",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    }
    )
    @GetMapping("/balance")
    public BigDecimal getRubleEquivalentAccount(@RequestParam String userName) {
        return service.getRublesEquivalentBalanceAllAccountsByUser(userName);
    }
}
