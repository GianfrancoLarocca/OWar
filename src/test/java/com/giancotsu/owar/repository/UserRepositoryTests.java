package com.giancotsu.owar.repository;


import com.giancotsu.owar.entity.Role;
import com.giancotsu.owar.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    UserEntity user = new UserEntity();
    Role adminRole = new Role();

    @BeforeAll
    public void setup() {

        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }
        adminRole = roleRepository.findByName("ADMIN").get();
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);


        user.setUsername("UsernameTest");
        user.setEmail("emailTest");
        user.setPassword("passwordTest");
        user.setRoles(roles);
        userRepository.save(user);

        System.out.println("setup complete");
        System.out.println(user);
    }

    @Test
    void testFindByUsernameSuccess() {

        Optional<UserEntity> userTestOptional = userRepository.findByUsername(user.getUsername());
        if (userTestOptional.isPresent()) {
            UserEntity userTest = userTestOptional.get();
            Assertions.assertThat(userTest.getUsername()).isEqualTo(user.getUsername());
            Assertions.assertThat(userTest.getEmail()).isEqualTo(user.getEmail());
            Assertions.assertThat(userTest.getPassword()).isEqualTo(user.getPassword());
            Assertions.assertThat(userTest.getRoles().stream().findFirst().get()).isEqualTo(user.getRoles().stream().findFirst().get());
        }
    }

    @AfterAll
    public void cleanup() {
        userRepository.delete(user);
        roleRepository.delete(adminRole);
    }
}
