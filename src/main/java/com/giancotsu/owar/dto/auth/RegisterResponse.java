package com.giancotsu.owar.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class RegisterResponse {

    private List<String> messages;

    public RegisterResponse() {
    }
    public RegisterResponse(List<String> messages) {
        this.messages = messages;
    }

}
