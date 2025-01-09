package com.javaacademy.cryptowallet.entity;

import com.javaacademy.cryptowallet.crypto.CryptoCurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoAccountEntity {
        private String login;
        private CryptoCurrencyType cryptoCurrencyType;
        private BigDecimal balance;
        private UUID id;
}
