package com.javaacademy.cryptowallet.service.integration;

import com.javaacademy.cryptowallet.config.AppConfigRub;
import com.javaacademy.cryptowallet.service.interfaces.ConvertToRublesCryptoService;
import com.jayway.jsonpath.JsonPath;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Profile("prod")
public class IntegrationRubleConvertService implements ConvertToRublesCryptoService {
    private static final String USD_RATE = "$['rates'].['USD']";
    private final OkHttpClient client;
    private final AppConfigRub configRub;
    private static final int SCALE_EIGHT = 8;

    @Override
    public BigDecimal convertRubleToUsd(BigDecimal ruble) {
        BigDecimal usdRate = getUsdRate();
        return ruble.multiply(usdRate);
    }

    @Override
    public BigDecimal convertUsdToRuble(BigDecimal usd) {
        BigDecimal usdRate = getUsdRate();
        return usd.divide(usdRate, SCALE_EIGHT, RoundingMode.HALF_UP);
    }

    public BigDecimal getUsdRate() {
        Request request = getRequest();
        return getResponse(request);
    }

    public Request getRequest() {
        return new Request.Builder()
                .get()
                .url(configRub.getUrl())
                .build();
    }

    @SneakyThrows
    private BigDecimal getResponse(Request request) {
        @Cleanup Response response = client.newCall(request).execute();
        if (response.isSuccessful() && response.body() != null) {
            String responseBody = response.body().string();
            return JsonPath.parse(responseBody).read(JsonPath.compile(USD_RATE), BigDecimal.class);
        }
        throw new RuntimeException("Неудачный запрос");
    }
}
