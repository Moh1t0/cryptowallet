package com.javaacademy.cryptowallet.repository;

import com.javaacademy.cryptowallet.entity.UserEntity;
import com.javaacademy.cryptowallet.storage.UserStorageImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Data
public class UserRepository {
    private final UserStorageImpl userStorage;

    public List<UserEntity> findAll() {
        return new ArrayList<>(userStorage.getData().values());
    }

    public void save(UserEntity userEntity) {
        if (userStorage.getData().containsKey(userEntity.getLogin())) {
            throw new IllegalArgumentException("Пользователь с таким логином уже есть!");
        }
        userStorage.getData().put(userEntity.getLogin(), userEntity);
    }

    public Optional<UserEntity> findByLogin(String login) {
        return Optional.ofNullable(userStorage.getData().get(login));
    }

    public void update(UserEntity userEntity) {
        userStorage.getData().put(userEntity.getLogin(), userEntity);
    }
}
