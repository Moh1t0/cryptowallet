package com.javaacademy.cryptowallet.repository;


import com.javaacademy.cryptowallet.entity.CryptoAccountEntity;
import com.javaacademy.cryptowallet.storage.CryptoStorage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Repository
@RequiredArgsConstructor
public class CryptoRepository {
    private final CryptoStorage cryptoStorage;

    public void saveAccount(CryptoAccountEntity account) {
        if (cryptoStorage.getData().containsKey(account.getId())) {
            throw new IllegalArgumentException("Аккаунт с id: " + account.getId() + " уже создан !");
        }
        cryptoStorage.getData().put(account.getId(), account);
    }

    public CryptoAccountEntity getAccount(UUID id) {
        return cryptoStorage.getData().get(id);
    }

    public List<CryptoAccountEntity> getAllAccount(String login) {
       return cryptoStorage.getData()
               .values()
               .stream()
               .filter(cryptoAccountEntity -> Objects.equals(cryptoAccountEntity.getLogin(), login))
               .toList();
    }
}
