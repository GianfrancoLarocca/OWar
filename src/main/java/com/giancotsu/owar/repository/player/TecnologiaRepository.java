package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.sviluppo.Tecnologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {

    Optional<Tecnologia> findByNome(String name);

    @Query(value = """
            SELECT *
            FROM sviluppo_astratto
            WHERE sviluppo_astratto.id = :id AND sviluppo_astratto.dtype LIKE 'Tecnologia'
            """, nativeQuery = true)
    Optional<Tecnologia> getTecnologiaById(@Param("id") Long id);

    @Query(value = """
            SELECT livello
            FROM player_tecnologia
            WHERE player_tecnologia.player_id = :playerId AND player_tecnologia.tecnologia_id = :tecnologiaId
            """, nativeQuery = true)
    Optional<Integer> getStrutturaLvl(@Param("playerId") Long playerId, @Param("tecnologiaId") Long tecnologiaId);

    @Query(value = """
            SELECT sa.livello_laboratorio_requisito AS requisito
            FROM player_tecnologia AS pt
            INNER JOIN sviluppo_astratto AS sa
            ON pt.player_id = :playerId AND sa.id = pt.tecnologia_id AND sa.id = :tecnologiaId
            """, nativeQuery = true)
    Optional<Integer> getRequisitoLvlByTechId(@Param("playerId") Long playerId, @Param("tecnologiaId") Long tecnologiaId);




}
