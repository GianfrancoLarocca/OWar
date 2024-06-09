package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerStrutture;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerStruttureRepository extends JpaRepository<PlayerStrutture, Long> {

    @Query(value = """
            SELECT sa.id, ps.livello, sa.nome, sa.url_immagine AS url
            FROM player_strutture AS ps
            INNER JOIN sviluppo_astratto AS sa
            ON sa.id = ps.strutture_id AND ps.player_id = :playerId
            """, nativeQuery = true)
    List<BasicBuildingInfoProjection> getAllBasicBuildingsInfo(Long playerId);

    @Query(value = """
            SELECT livello, moltiplicatore_costo AS moltiplicatore, map_costo.costo_base as costo, map_costo.costo_base_key as risorsa
            FROM player_strutture AS ps
            INNER JOIN sviluppo_astratto AS sa
            ON sa.id = ps.strutture_id AND ps.player_id = :playerId
            INNER JOIN map_costo_base_strutture AS map_costo
            ON map_costo.strutture_id = sa.id AND sa.id = :strutturaId
            """, nativeQuery = true)
    List<SviluppoCostiProjection> getCostiSviluppo(Long playerId, Long strutturaId);

    @Query(value = """
            SELECT *
            FROM player_strutture
            WHERE player_strutture.player_id = :playerId AND player_strutture.strutture_id = :struttureId
            """, nativeQuery = true)
    Optional<PlayerStrutture> findByPlayerIdAndSviluppoId(Long playerId, Long struttureId);
}


