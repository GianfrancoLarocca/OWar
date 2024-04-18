package com.giancotsu.owar.controller;

import com.giancotsu.owar.service.AuthService;
import com.giancotsu.owar.dto.auth.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserRegisterDto userDto) {
        return authService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return authService.login(authRequestDto);
    }

    @PostMapping(value = "/account-activation/{email}/{code}")
    public ResponseEntity<UserActivationMessage> accountActivation(@PathVariable("email") String userEmail, @PathVariable("code") String activationCode) {
        ActivationToken activationToken = new ActivationToken(userEmail, activationCode);
        return authService.activeAccount(activationToken);
    }

    @PostMapping(value = "reset/password/email/{email}")
    public ResponseEntity<String> resetPasswordEmail(@PathVariable("email") String userEmail) {
        return authService.resetPasswordEmail(userEmail);
    }

    @PostMapping(value = "reset/password/{email}/{code}")
    public ResponseEntity<String> resetPassword(@PathVariable("email") String email, @PathVariable("code") String secureCode, @RequestBody ResetPasswordDto resetPasswordDto) {
        return authService.resetPassword(resetPasswordDto, email, secureCode);
    }
}
