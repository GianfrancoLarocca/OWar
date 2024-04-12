package com.giancotsu.owar.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDto {

    @Size(min=4, max=20, message = "L'Username deve essere tra 4 e 20 caratteri")
    private String username;
    @Email(message = "Email non valida")
    private String email;
    @Size(min=8, max=100, message = "La Password deve essere tra 8 e 100 caratteri")
    private String password;
}
