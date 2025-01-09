package com.javaacademy.cryptowallet.dto;

import com.javaacademy.cryptowallet.crypto.CryptoCurrencyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Дто крипто аккаунта кошелька")
public class CryptoAccountDto {

    @Schema(description = "Логин пользователя")
    private String login;

    @Schema(description = "Криптовалюта")
    private CryptoCurrencyType cryptoCurrencyType;

    @Schema(description = "Баланс")
    private BigDecimal balance;

    @Schema(description = "Уникальный id")
    private UUID id;
}
