package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.dto.UserDto;
import com.javaacademy.cryptowallet.entity.UserEntity;
import com.javaacademy.cryptowallet.mapper.UserMapper;
import com.javaacademy.cryptowallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public void saveNewUser(UserDto userDto) {
       repository.save(mapper.convertToEntity(userDto));
    }

    public UserEntity getByLogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким логином не найден!"));
    }

    public void resetPassword(String login, String oldPassword, String newPassword) {
        UserEntity user = getByLogin(login);

        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Неверный пароль!");
        }
        user.setPassword(newPassword);
        repository.update(user);
    }
}
