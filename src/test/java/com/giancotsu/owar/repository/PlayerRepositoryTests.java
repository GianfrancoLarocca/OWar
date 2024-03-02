package com.giancotsu.owar.repository;

import com.giancotsu.owar.entity.Player;
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
    Player player;

    @BeforeAll
    public void setUp() {
        player = new Player();
        player.setNickname("Giancotsu");
        playerRepository.save(player);
    }

    @Test
    void getUserById_shouldReturnUser() {

        Player playerTest = new Player();
        Optional<Player>  playerTestOptional = playerRepository.findById(player.getId());
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
