package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerRisorse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRisorseRepository extends JpaRepository<PlayerRisorse, Long> {

    @Query(value = """
            SELECT pr.acqua, pr.bitcoin, pr.civili, pr.energia, pr.metallo, pr.microchip, pr.id
            FROM "player-risorse" AS pr, player
            WHERE player.player_risorse_id =pr.id
            """, nativeQuery = true)
    Optional<PlayerRisorse> findByPlayerId(Long playerId);

}
