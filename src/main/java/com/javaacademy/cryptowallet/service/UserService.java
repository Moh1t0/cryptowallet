package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.database.UserDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDatabase userDatabase;

    public void saveNewUser() {

    }
}
