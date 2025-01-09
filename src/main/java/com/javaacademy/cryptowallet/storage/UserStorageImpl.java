package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.UserEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorageImpl {
    @Getter
    private Map<String, UserEntity> data = new HashMap<>();
}
