package com.giancotsu.owar.dto.auth;

import lombok.Data;

import java.util.Date;

@Data
public class AuthResponseDto {
    private String username;
    private String role;
    private String token;
    private String type;
    private Date expiration;
}
