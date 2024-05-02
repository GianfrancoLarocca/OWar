package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.Attivita;
import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.RegistroAttivita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    @Query(value = """
            SELECT player.id
            FROM player
            """, nativeQuery = true)
    Set<Long> getAllPlayerId();

}
