package com.javaacademy.cryptowallet.service.interfaces;

import java.math.BigDecimal;

@FunctionalInterface
public interface UsdCryptoService {
    BigDecimal getCryptoPriceInUsd(String cryptoName);
}
