package com.javaacademy.cryptowallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Дто для изменения баланса")
public class AccountReplenishmentDto {

    @Schema(description = "Уникальный id")
    @JsonProperty("account_id")
    private UUID id;

    @Schema(description = "Сумма в рублях")
    @JsonProperty("rubles_amount")
    private BigDecimal rublesAmount;
}
