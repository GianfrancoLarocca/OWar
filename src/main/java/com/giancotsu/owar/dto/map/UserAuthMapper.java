package com.giancotsu.owar.dto.map;

import com.giancotsu.owar.dto.auth.UserRegisterDto;
import com.giancotsu.owar.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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
