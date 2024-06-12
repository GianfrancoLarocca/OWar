package com.giancotsu.owar.utils;

import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class JwtUserUtils {

    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepository;

    public JwtUserUtils(JWTGenerator jwtGenerator, UserRepository userRepository) {
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
    }

    public String getJWTFromHeaderRequest(String authorizationToken) {
        if (StringUtils.hasText(authorizationToken) && authorizationToken.startsWith("Bearer ")) {
            return authorizationToken.substring(7);
        } else {
            return null;
        }
    }

    public UserEntity getUserFromAuthorizationToken(String authorizationToken) {
        String jwt = this.getJWTFromHeaderRequest(authorizationToken);
        String username = jwtGenerator.extractUsername(jwt);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
