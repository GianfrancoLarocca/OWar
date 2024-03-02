package com.giancotsu.owar.repository;

import com.giancotsu.owar.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long>, JpaRepository<Player, Long> {

    Optional<Player> findByNickname(String nickname);

    @Query(value = "SELECT COUNT(*) FROM player", nativeQuery = true)
    int playerCount();
}
