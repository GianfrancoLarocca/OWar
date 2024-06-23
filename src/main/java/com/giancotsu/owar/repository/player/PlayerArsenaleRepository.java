package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerArsenale;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerArsenaleRepository extends JpaRepository<PlayerArsenale, Long> {

    Optional<PlayerArsenale> findPlayerArsenaleByPlayerIdAndArsenaleId(Long playerId, Long arsenaleId);

    @Query(value = """
            SELECT sa.id, pa.livello, sa.nome, sa.url_immagine AS url, pa.livello_fabbrica_requisito AS requisito
            FROM player_arsenale AS pa
            INNER JOIN sviluppo_astratto AS sa
            ON sa.id = pa.arsenale_id AND pa.player_id = :playerId AND sa.dtype LIKE 'Arsenale'
            """, nativeQuery = true)
    List<BasicBuildingInfoProjection> getAllBasicBuildingsInfo(Long playerId);

    @Query(value = """
            SELECT livello, moltiplicatore_costo AS moltiplicatore, map_costo.costo_base as costo, map_costo.costo_base_key as risorsa
            FROM player_arsenale AS pa
            INNER JOIN sviluppo_astratto AS sa
            ON sa.id = pa.arsenale_id AND pa.player_id = :playerId AND sa.dtype LIKE 'Arsenale'
            INNER JOIN map_costo_base_strutture AS map_costo
            ON map_costo.strutture_id = sa.id AND sa.id = :arsenaleId
            """, nativeQuery = true)
    List<SviluppoCostiProjection> getCostiSviluppo(Long playerId, Long arsenaleId);

    @Query(value = """
            SELECT *
            FROM player_arsenale
            WHERE player_arsenale.player_id = :playerId AND player_arsenale.arsenale_id = :arsenaleId
            """, nativeQuery = true)
    Optional<PlayerArsenale> findByPlayerIdAndSviluppoId(Long playerId, Long arsenaleId);
}
