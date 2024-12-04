package com.javaacademy.cryptowallet.database;


import com.javaacademy.cryptowallet.entity.UserEntity;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDatabase {
    private Map<String, UserEntity> data = new HashMap<>();

    public void save(UserEntity userEntity) {
        if (data.containsKey(userEntity.getLogin())) {
            throw new IllegalArgumentException("Пользователь с логином " + userEntity.getLogin() + "уже занят !");
        }
        data.put(userEntity.getLogin(), userEntity);
    }

    public Optional<UserEntity> getByLogin(String login) {
        return Optional.ofNullable(data.get(login));
    }
}
