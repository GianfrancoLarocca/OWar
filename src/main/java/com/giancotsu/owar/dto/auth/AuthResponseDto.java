package com.giancotsu.owar.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private String type;
    private String role;
}
