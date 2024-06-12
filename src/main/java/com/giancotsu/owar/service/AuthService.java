package com.giancotsu.owar.service;

import com.giancotsu.owar.dto.auth.*;
import com.giancotsu.owar.dto.map.UserAuthMapper;
import com.giancotsu.owar.email.EmailService;
import com.giancotsu.owar.entity.player.PlayerBasicInformationEntity;
import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.user.Role;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.entity.user.UserResetPasswordEntity;
import com.giancotsu.owar.event.user.UserRegisteredEvent;
import com.giancotsu.owar.repository.RoleRepository;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.security.JWTGenerator;
import com.giancotsu.owar.service.player.PlayerSviluppoService;
import com.giancotsu.owar.service.player.StruttureService;
import com.giancotsu.owar.service.player.TecnologiaService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator tokenGenerator;
    private final EmailService emailService;
    private final PlayerSviluppoService playerSviluppoService;
    private final StruttureService struttureService;
    private final TecnologiaService tecnologiaService;
    private final Validator validator;

    private final ApplicationEventPublisher eventPublisher;
    Random random = new Random();
    @Value("${frontend.url}")
    private String frontendUrl;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTGenerator tokenGenerator, EmailService emailService, PlayerSviluppoService playerSviluppoService, StruttureService struttureService, TecnologiaService tecnologiaService, Validator validator, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.emailService = emailService;
        this.playerSviluppoService = playerSviluppoService;
        this.struttureService = struttureService;
        this.tecnologiaService = tecnologiaService;
        this.validator = validator;
        this.eventPublisher = eventPublisher;


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

        RegisterResponse registerResponse = new RegisterResponse();

        Set<ConstraintViolation<UserRegisterDto>> violations = validator.validate(userRegisterDto);

        if (!violations.isEmpty()) {
            List<String> invalidFields = new ArrayList<>();
            for (ConstraintViolation<UserRegisterDto> constraintViolation : violations) {

                invalidFields.add(constraintViolation.getMessage());
            }
            registerResponse.setMessages(invalidFields);

            return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(userRegisterDto.getUsername()) || userRepository.existsByEmail(userRegisterDto.getEmail())) {
            registerResponse.setMessages(new ArrayList<>(Collections.singleton("Username or Email already exists")));
            return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
        }

        UserEntity newUser = UserAuthMapper.mapToUser(userRegisterDto);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("User Role not exists."));
        newUser.setRoles(List.of(userRole));

        newUser.setActive(false);
        int randomActivationCode = random.nextInt(9999 - 1111) + 1111;
        newUser.setActivationCode(Integer.toString(randomActivationCode));

        PlayerEntity player = new PlayerEntity();
        player.setBasicInformation(new PlayerBasicInformationEntity(newUser.getUsername(), LocalDateTime.now()));
        newUser.setPlayer(player);
        System.err.println(player);
        UserEntity savedUser = userRepository.save(newUser);
        playerSviluppoService.salvaSviluppo(savedUser);
        struttureService.salvaStrutture(savedUser);
        tecnologiaService.salvaTecnologie(savedUser);

        String activationUrl = "%s/activation/%s/%s".formatted(frontendUrl, newUser.getEmail(), newUser.getActivationCode());
        String emailBody = """                
                <div style="text-align: center;">
                <h1>Ciao %s!</h1>
                                
                                
                <p>Ti diamo il benvenuto nel mondo di OWar, la tua prima tappa verso la conquista del mondo!</p>
                <p>Conferma subito il tuo nuovo account e ricevi le seguenti <strong>RISORSE</strong>:</p>
                                
                <ul style="display: inline-block; text-align: left;">
                  <li><strong>DOLLARI: $10.000</strong></li>
                  <li><strong>PETROLIO: 10 LT.</strong></li>
                </ul>
                                
                <p>Per attivare l'account non devi far altro che cliccare sul seguente link:</p>
                <a href="%s">Attiva il tuo account</a>
                </div>
                """.formatted(newUser.getUsername() ,activationUrl);
        //emailService.sendHtmlMessage(newUser.getEmail(), "OWar - Conferma il tuo account", emailBody);

        registerResponse.setMessages(new ArrayList<>(Collections.singleton("User registered successfully")));

        eventPublisher.publishEvent(new UserRegisteredEvent(this, savedUser));

        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

    public ResponseEntity<AuthResponseDto> login(AuthRequestDto authRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication, authRequestDto.getExpTime());
        Date exp = tokenGenerator.extractExpiration(token);

        AuthResponseDto authResponse = new AuthResponseDto();
        authResponse.setToken(token);
        authResponse.setType("Bearer ");
        authResponse.setRole(authentication.getAuthorities().stream().findFirst().get().getAuthority());
        authResponse.setUsername(authentication.getName());
        authResponse.setExpiration(new Date(exp.getTime()));

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

    public ResponseEntity<String> resetPasswordEmail(String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity userEntity = user.get();

        ResetPasswordResponse resetPassword = new ResetPasswordResponse();

        int randomActivationCode = random.nextInt(99999999 - 11111111) + 11111111;
        resetPassword.setSecureCode(Integer.toString(randomActivationCode));
        UserResetPasswordEntity userResetPasswordEntity;
        if(userEntity.getResetPasswordEntity() != null) {
            userEntity.getResetPasswordEntity().setSecureCode(Integer.toString(randomActivationCode));
        } else {
            userResetPasswordEntity = new UserResetPasswordEntity(Integer.toString(randomActivationCode));
            userEntity.setResetPasswordEntity(userResetPasswordEntity);
        }
        userRepository.save(userEntity);

        resetPassword.setEmail(email);

        String username = userEntity.getUsername();
        resetPassword.setUsername(username);

        String resetUrl = "%s/reset/password/%s/%s".formatted(frontendUrl, resetPassword.getEmail(), resetPassword.getSecureCode());
        String emailBody = """
                <div style="text-align: center;">
                    <h2>Salve %s!</h2>
                    <p>Per resettare la tua password clicca sul seguente link:</p>
                    <a href="%s">Resetta Password</a>
                """.formatted(username, resetUrl);

        //emailService.sendHtmlMessage(email, "OWar - Reset Password", emailBody);

        return new ResponseEntity<>("Email sent", HttpStatus.OK);

    }

    public ResponseEntity<String> resetPassword(ResetPasswordDto resetPasswordDto, String email, String secureCode) {

        Set<ConstraintViolation<ResetPasswordDto>> violations = validator.validate(resetPasswordDto);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<ResetPasswordDto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        UserEntity userEntity = user.get();
        if(userEntity.getResetPasswordEntity().getExpirationDate().before(new Date()) || !userEntity.getResetPasswordEntity().getSecureCode().equals(secureCode)) {
            return new ResponseEntity<>("Codice scaduto o non valido", HttpStatus.BAD_REQUEST);
        }

        if(resetPasswordDto.getPassword().equals(resetPasswordDto.getPasswordConfirm())){
            userEntity.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        } else {
            return new ResponseEntity<>("Le due password non coincidono", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(userEntity);

        return new ResponseEntity<>("Password cambiata!", HttpStatus.OK);
    }
}
