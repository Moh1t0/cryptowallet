package com.javaacademy.cryptowallet.service.interfaces;

import java.math.BigDecimal;

public interface ConvertToRublesCryptoService {
    BigDecimal convertUsdToRuble(BigDecimal usd);

    BigDecimal convertRubleToUsd(BigDecimal ruble);
}
