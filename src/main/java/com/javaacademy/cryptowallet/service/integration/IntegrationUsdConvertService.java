package com.javaacademy.cryptowallet.service.integration;

import com.javaacademy.cryptowallet.config.AppConfigUsd;
import com.javaacademy.cryptowallet.service.interfaces.UsdCryptoService;
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

@Service
@RequiredArgsConstructor
@Profile("prod")
public class IntegrationUsdConvertService implements UsdCryptoService {
    private static final String USD_FORMAT = "$['%s']['usd']";
    private static final String URL_FORMAT = "%s/simple/price?ids=%s&vs_currencies=usd";
    private final OkHttpClient client;
    private final AppConfigUsd appConfigUsd;

    @Override
    public BigDecimal getCryptoPriceInUsd(String cryptoName) {
        Request request = getRequest(cryptoName);
        return getResponseUsd(request, cryptoName);
    }

    private Request getRequest(String cryptoName) {
        String format = String.format(URL_FORMAT, appConfigUsd.getUrl(), cryptoName);
        return new Request.Builder()
                .get()
                .url(format)
                .addHeader(appConfigUsd.getHeader(), appConfigUsd.getToken()).build();
    }

    @SneakyThrows
    private BigDecimal getResponseUsd(Request request, String cryptoName) {
        @Cleanup Response response = client.newCall(request).execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new RuntimeException("Response error:" + response);
        }
        String jsonResponse = response.body().string();
        String jsonPath = String.format(USD_FORMAT, cryptoName);
        return JsonPath.parse(jsonResponse).read(jsonPath, BigDecimal.class);
    }
}
