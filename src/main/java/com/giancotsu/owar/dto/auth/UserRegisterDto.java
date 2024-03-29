package com.giancotsu.owar.dto.auth;

import lombok.Data;

@Data
public class UserRegisterDto {

    private String username;
    private String email;
    private String password;
}
