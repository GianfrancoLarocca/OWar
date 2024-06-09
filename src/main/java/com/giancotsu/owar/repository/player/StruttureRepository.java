package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.sviluppo.Strutture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StruttureRepository extends JpaRepository<Strutture, Long> {

    Optional<Strutture> findByNome(String name);

    @Query(value = """
            SELECT *
            FROM sviluppo_astratto
            WHERE sviluppo_astratto.id = :id
            """, nativeQuery = true)
    Optional<Strutture> getStrutturaById(@Param("id") Long id);

    @Query(value = """
            SELECT livello
            FROM player_strutture
            WHERE player_strutture.player_id = :playerId AND player_strutture.strutture_id = :strutturaId
            """, nativeQuery = true)
    Optional<Integer> getStrutturaLvl(@Param("playerId") Long playerId, @Param("strutturaId") Long strutturaId);


}
