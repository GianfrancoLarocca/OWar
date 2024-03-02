package com.giancotsu.owar.repository;


import com.giancotsu.owar.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    UserEntity user;

    @BeforeAll
    public void setup() {
        user = new UserEntity();
        user.setEmail("email");
        user.setPassword("password");
        user.setAdmin(false);
        userRepository.save(user);
    }

    @Test
    void testFindByEmail() {

        Optional<UserEntity> userTestOptional = userRepository.findByEmail(user.getEmail());
        if (userTestOptional.isPresent()) {
            UserEntity userTest = userTestOptional.get();
            Assertions.assertThat(userTest.getEmail()).isEqualTo(user.getEmail());
            Assertions.assertThat(userTest.getPassword()).isEqualTo(user.getPassword());
            Assertions.assertThat(userTest.isAdmin()).isEqualTo(user.isAdmin());
        }
    }

    @AfterAll
    public void cleanup() {
        userRepository.delete(user);
    }
}
