package com.javaacademy.cryptowallet.mapper;

import com.javaacademy.cryptowallet.dto.CryptoAccountDto;
import com.javaacademy.cryptowallet.entity.CryptoAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class CryptoAccountMapper {

    public CryptoAccountDto convertToDto(CryptoAccountEntity cryptoAccountEntity) {
        return new CryptoAccountDto(cryptoAccountEntity.getLogin(), cryptoAccountEntity.getCryptoCurrencyType(),
                cryptoAccountEntity.getBalance(), cryptoAccountEntity.getId());
    }

    public CryptoAccountEntity convertToEntity(CryptoAccountDto cryptoAccountDto) {
        return new CryptoAccountEntity(cryptoAccountDto.getLogin(), cryptoAccountDto.getCryptoCurrencyType(),
                cryptoAccountDto.getBalance(), cryptoAccountDto.getId());
    }
}
