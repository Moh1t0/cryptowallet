package com.javaacademy.cryptowallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaacademy.cryptowallet.crypto.CryptoCurrencyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Дто для создания криптокошелька")
public class CryptoCreateDto {

    @Schema(description = "Логин")
    @NonNull
    @JsonProperty("username")
    private String login;

    @Schema(description = "Криптовалюта")
    @NonNull
    @JsonProperty("crypto_type")
    private CryptoCurrencyType currencyType;
}
