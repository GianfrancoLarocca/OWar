package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.sviluppo.Arsenale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArsenaleRepository extends JpaRepository<Arsenale, Long> {

    Optional<Arsenale> findByNome(String name);

    @Query(value = """
            SELECT *
            FROM sviluppo_astratto
            WHERE sviluppo_astratto.id = :id AND sviluppo_astratto.dtype LIKE 'Arsenale'
            """, nativeQuery = true)
    Optional<Arsenale> getArsenaleById(@Param("id") Long id);

    @Query(value = """
            SELECT livello
            FROM player_arsenale
            WHERE player_arsenale.player_id = :playerId AND player_arsenale.arsenale_id = :arsenaleId
            """, nativeQuery = true)
    Optional<Integer> getArsenaleLvl(@Param("playerId") Long playerId, @Param("arsenaleId") Long arsenaleId);

    @Query(value = """
            SELECT sa.livello_fabbrica_requisito AS requisito
            FROM player_arsenale AS pa
            INNER JOIN sviluppo_astratto AS sa
            ON pa.player_id = :playerId AND sa.id = pa.arsenale_id AND sa.id = :arsenaleId
            """, nativeQuery = true)
    Optional<Integer> getRequisitoLvlByArsenaleId(@Param("playerId") Long playerId, @Param("arsenaleId") Long arsenaleId);
}
