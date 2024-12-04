package com.javaacademy.cryptowallet.repository;

import com.javaacademy.cryptowallet.database.UserDatabase;
import com.javaacademy.cryptowallet.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepository {
    private UserDatabase userDatabase;

    public void save(UserEntity userEntity) {
        userDatabase.save(userEntity);
    }

    public Optional<UserEntity> getByLogin(String login) {
        return userDatabase.getByLogin(login);
    }
}
