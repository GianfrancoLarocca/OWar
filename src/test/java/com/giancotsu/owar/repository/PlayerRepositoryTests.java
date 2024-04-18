package com.giancotsu.owar.repository;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.repository.player.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlayerRepositoryTests {

    @Autowired
    PlayerRepository playerRepository;
    PlayerEntity player;

    @BeforeAll
    public void setUp() {
        player = new PlayerEntity();
        player.setNickname("Giancotsu");
        playerRepository.save(player);
    }

    @Test
    void getUserById_shouldReturnUser() {

        PlayerEntity playerTest = new PlayerEntity();
        Optional<PlayerEntity>  playerTestOptional = playerRepository.findById(player.getId());
        if(playerTestOptional.isPresent()){
            playerTest = playerTestOptional.get();
        }
        Assertions.assertThat(playerTest.getNickname()).isEqualTo("Giancotsu");
    }

    @AfterAll
    void tearDown() {
        playerRepository.deleteById(player.getId());
        System.out.println("tearDown... count: " + playerRepository.count() + ", is present? " + playerRepository.findById(1L).isPresent());

    }
}
