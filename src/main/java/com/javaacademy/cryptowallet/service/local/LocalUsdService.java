package com.javaacademy.cryptowallet.service.local;

import com.javaacademy.cryptowallet.service.interfaces.UsdCryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


@Service
@Profile("local")
public class LocalUsdService implements UsdCryptoService {

    @Value("${app.converter.local-value}")
    private BigDecimal localValue;

    @Override
    public BigDecimal getCryptoPriceInUsd(String cryptoName) {
        return localValue;
    }
}
