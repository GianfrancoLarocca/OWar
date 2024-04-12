package com.giancotsu.owar.dto.auth;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordDto {

    @Size(min=8, max=100, message = "La Password deve essere tra 8 e 100 caratteri")
    private String password;
    private String passwordConfirm;
}
