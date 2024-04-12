package com.giancotsu.owar.dto.map;

import com.giancotsu.owar.dto.auth.UserRegisterDto;
import com.giancotsu.owar.entity.user.UserEntity;

public class UserAuthMapper {

    private UserAuthMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserEntity mapToUser(UserRegisterDto userRegisterDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        return user;
    }

    public static UserRegisterDto mapToUserRegisterDto(UserEntity user) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername(user.getUsername());
        userRegisterDto.setEmail(user.getEmail());
        userRegisterDto.setPassword(user.getPassword());
        return userRegisterDto;
    }
}
