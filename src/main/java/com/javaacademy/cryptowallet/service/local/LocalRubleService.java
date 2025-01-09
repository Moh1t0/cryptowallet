package com.javaacademy.cryptowallet.service.local;

import com.javaacademy.cryptowallet.service.interfaces.ConvertToRublesCryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Profile("local")
public class LocalRubleService implements ConvertToRublesCryptoService {
    private static final int SCALE_TEN = 10;
    @Value("${app.usd.fixedRate}")
    private BigDecimal fixedRate;

    @Override
    public BigDecimal convertUsdToRuble(BigDecimal usd) {
        return usd.divide(fixedRate, SCALE_TEN, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal convertRubleToUsd(BigDecimal ruble) {
        return ruble.multiply(fixedRate);
    }
}
