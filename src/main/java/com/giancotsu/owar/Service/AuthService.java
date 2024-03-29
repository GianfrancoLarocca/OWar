package com.giancotsu.owar.Service;

import com.giancotsu.owar.dto.auth.AuthRequestDto;
import com.giancotsu.owar.dto.auth.AuthResponseDto;
import com.giancotsu.owar.dto.auth.UserRegisterDto;
import com.giancotsu.owar.dto.map.UserAuthMapper;
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

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator tokenGenerator;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;

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

    public ResponseEntity<String> register(UserRegisterDto userRegisterDto) {

        if(userRepository.existsByUsername(userRegisterDto.getUsername()) || userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new UsernameOrEmailAlreadyExistsException("Username or Email already exists");
        }

        UserEntity newUser = UserAuthMapper.mapToUser(userRegisterDto);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("User Role not exists."));
        newUser.setRoles(List.of(userRole));
        userRepository.save(newUser);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    public ResponseEntity<AuthResponseDto> login(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);

        AuthResponseDto authResponse = new AuthResponseDto();
        authResponse.setToken(token);
        authResponse.setType("Bearer ");
        authResponse.setRole(authentication.getAuthorities().stream().findFirst().get().getAuthority());

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
