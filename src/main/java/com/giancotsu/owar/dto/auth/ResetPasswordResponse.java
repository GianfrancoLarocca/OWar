package com.giancotsu.owar.dto.auth;

import lombok.Data;

@Data
public class ResetPasswordResponse {

    private String username;
    private String email;
    private String secureCode;

}
