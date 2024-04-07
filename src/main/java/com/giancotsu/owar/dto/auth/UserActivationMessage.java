package com.giancotsu.owar.dto.auth;

import lombok.Data;

@Data
public class UserActivationMessage {
    private String userEmail;
    private String accountStatus;
    private String message;
}
