package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.sviluppo.Difesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DifesaRepository extends JpaRepository<Difesa, Long> {

    Optional<Difesa> findByNome(String name);

    @Query(value = """
            SELECT *
            FROM sviluppo_astratto
            WHERE sviluppo_astratto.id = :id AND sviluppo_astratto.dtype LIKE 'Difesa'
            """, nativeQuery = true)
    Optional<Difesa> getDifesaById(@Param("id") Long id);

    @Query(value = """
            SELECT livello
            FROM player_difesa
            WHERE player_difesa.player_id = :playerId AND player_difesa.difesa_id = :difesaId
            """, nativeQuery = true)
    Optional<Integer> getDifesaLvl(@Param("playerId") Long playerId, @Param("difesaId") Long difesaId);

    @Query(value = """
            SELECT sa.livello_fabbrica_requisito AS requisito
            FROM player_difesa AS pd
            INNER JOIN sviluppo_astratto AS sa
            ON pd.player_id = :playerId AND sa.id = pd.difesa_id AND sa.id = :difesaId
            """, nativeQuery = true)
    Optional<Integer> getRequisitoLvlByDifesaId(@Param("playerId") Long playerId, @Param("difesaId") Long difesaId);
}
