package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.CryptoAccountEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Data
@RequiredArgsConstructor
public class CryptoStorage {
    private final Map<UUID, CryptoAccountEntity> data = new HashMap<>();
}
