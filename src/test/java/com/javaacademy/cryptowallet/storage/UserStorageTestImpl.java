package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorageTestImpl implements UserStorage {
    private final Map<String, UserEntity> data = new HashMap<>();

    @PostConstruct
    public void init() {
        UserEntity userEntity = UserEntity.builder()
                .login("testuser")
                .email("testuser@mail.com")
                .password("oldPassword")
                .build();
        data.put(userEntity.getLogin(), userEntity);
    }

    @Override
    public Map<String, UserEntity> getData() {
        return data;
    }
}
