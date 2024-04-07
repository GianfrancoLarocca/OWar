package com.giancotsu.owar.Service;

import com.giancotsu.owar.dto.auth.*;
import com.giancotsu.owar.dto.map.UserAuthMapper;
import com.giancotsu.owar.email.EmailService;
import com.giancotsu.owar.entity.Role;
import com.giancotsu.owar.entity.UserEntity;
import com.giancotsu.owar.exception.UsernameOrEmailAlreadyExistsException;
import com.giancotsu.owar.repository.RoleRepository;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator tokenGenerator;
    private final EmailService emailService;

    Random random = new Random();

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTGenerator tokenGenerator, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.emailService = emailService;

        if (roleRepository.findByName("USER").isEmpty()) {
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }

        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }
    }

    public ResponseEntity<RegisterResponse> register(UserRegisterDto userRegisterDto) {

        if (userRepository.existsByUsername(userRegisterDto.getUsername()) || userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new UsernameOrEmailAlreadyExistsException("Username or Email already exists");
        }

        UserEntity newUser = UserAuthMapper.mapToUser(userRegisterDto);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("User Role not exists."));
        newUser.setRoles(List.of(userRole));

        newUser.setActive(false);
        int randomActivationCode = random.nextInt(9999 - 1111) + 1111;
        newUser.setActivationCode(Integer.toString(randomActivationCode));
        userRepository.save(newUser);

        String activationUrl = "http://localhost:4200/activation/%s/%s".formatted(newUser.getEmail(), newUser.getActivationCode());
        String emailBody = """                
                <div style="text-align: center;">
                <h1>Ciao!</h1>
                                
                                
                <p>Ti diamo il benvenuto nella Lobby di OWar, la tua prima tappa verso il mondo dei migliori browser game!</p>
                <p>Conferma subito il tuo nuovo account e ricevi le seguenti <strong>risorse</strong>:</p>
                                
                <ul style="display: inline-block; text-align: left;">
                  <li><strong>Dollari: $10.000</strong></li>
                  <li><strong>Petrolio: 10 LT.</strong></li>
                </ul>
                                
                <p>Per attivare l'account non devi far altro che cliccare sul seguente link:</p>
                <a href="%s">Attiva il tuo account</a>
                </div>
                """.formatted(activationUrl);
        emailService.sendHtmlMessage(newUser.getEmail(), "OWar - Conferma il tuo account", emailBody);

        return new ResponseEntity<>(new RegisterResponse("User registered successfully"), HttpStatus.OK);
    }

    public ResponseEntity<AuthResponseDto> login(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        Date exp = tokenGenerator.extractExpiration(token);


        AuthResponseDto authResponse = new AuthResponseDto();
        authResponse.setToken(token);
        authResponse.setType("Bearer ");
        authResponse.setRole(authentication.getAuthorities().stream().findFirst().get().getAuthority());
        authResponse.setUsername(authentication.getName());
        authResponse.setExpiration(exp.getTime());

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    public ResponseEntity<UserActivationMessage> activeAccount(ActivationToken activationToken) {

        Optional<UserEntity> userToCheck = userRepository.findByEmail(activationToken.getUserEmail());
        if (userToCheck.isEmpty()) {
            return activationResponse(activationToken, "inactivated","User not found", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userToCheck.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!user.getUsername().equals(authentication.getName())) {
            return activationResponse(activationToken, "inactivated", "Bad activation code", HttpStatus.BAD_REQUEST);
        }


        if (user.getActivationCode().equals(activationToken.getActivationCode())) {

            user.setActive(true);
            userRepository.save(user);

            return activationResponse(activationToken, "activated", "Account activated successfully", HttpStatus.OK);

        } else {
            return activationResponse(activationToken, "inactivated", "Bad activation code", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<UserActivationMessage> activationResponse(ActivationToken activationToken, String accountStatus, String responseMessage, HttpStatus httpStatus) {
        UserActivationMessage message = new UserActivationMessage();
        message.setUserEmail(activationToken.getUserEmail());
        message.setAccountStatus(accountStatus);
        message.setMessage(responseMessage);

        return new ResponseEntity<>(message, httpStatus);
    }
}
