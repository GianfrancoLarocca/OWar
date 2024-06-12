package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerTecnologia;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerTecnologiaRepository extends JpaRepository<PlayerTecnologia, Long> {

    @Query(value = """
            SELECT sa.id, pt.livello, sa.nome, sa.url_immagine AS url
            FROM player_tecnologia AS pt
            INNER JOIN sviluppo_astratto AS sa
            ON sa.id = pt.tecnologia_id AND pt.player_id = :playerId AND sa.dtype LIKE 'Tecnologia'
            """, nativeQuery = true)
    List<BasicBuildingInfoProjection> getAllBasicBuildingsInfo(Long playerId);

    @Query(value = """
            SELECT livello, moltiplicatore_costo AS moltiplicatore, map_costo.costo_base as costo, map_costo.costo_base_key as risorsa
            FROM player_tecnologia AS pt
            INNER JOIN sviluppo_astratto AS sa
            ON sa.id = pt.tecnologia_id AND pt.player_id = :playerId AND sa.dtype LIKE 'Tecnologia'
            INNER JOIN map_costo_base_strutture AS map_costo
            ON map_costo.strutture_id = sa.id AND sa.id = :tecnologiaId
            """, nativeQuery = true)
    List<SviluppoCostiProjection> getCostiSviluppo(Long playerId, Long tecnologiaId);

    @Query(value = """
            SELECT *
            FROM player_tecnologia
            WHERE player_tecnologia.player_id = :playerId AND player_tecnologia.tecnologia_id = :tecnologiaId
            """, nativeQuery = true)
    Optional<PlayerTecnologia> findByPlayerIdAndSviluppoId(Long playerId, Long tecnologiaId);
}
