package com.javaacademy.cryptowallet.mapper;

import com.javaacademy.cryptowallet.dto.UserDto;
import com.javaacademy.cryptowallet.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity convertToEntity(UserDto userDto) {
        return new UserEntity(userDto.getLogin(), userDto.getEmail(), userDto.getPassword());
    }

    public UserDto convertToDto(UserEntity userEntity) {
        return new UserDto(userEntity.getLogin(), userEntity.getEmail(), userEntity.getPassword());
    }
}
