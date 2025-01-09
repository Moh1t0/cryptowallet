package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.UserEntity;

import java.util.Map;

@FunctionalInterface
public interface UserStorage {
    Map<String, UserEntity> getData();
}
