package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerDifesa;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerDifesaRepository extends JpaRepository<PlayerDifesa, Long> {

    @Query(value = """
            SELECT sa.id, pd.livello, sa.nome, sa.url_immagine AS url
            FROM player_difesa AS pd
            INNER JOIN sviluppo_astratto AS sa
            ON sa.id = pd.difesa_id AND pd.player_id = :playerId AND sa.dtype LIKE 'Difesa'
            """, nativeQuery = true)
    List<BasicBuildingInfoProjection> getAllBasicBuildingsInfo(Long playerId);

    @Query(value = """
            SELECT livello, moltiplicatore_costo AS moltiplicatore, map_costo.costo_base as costo, map_costo.costo_base_key as risorsa
            FROM player_difesa AS pd
            INNER JOIN sviluppo_astratto AS sa
            ON sa.id = pd.difesa_id AND pd.player_id = :playerId AND sa.dtype LIKE 'Difesa'
            INNER JOIN map_costo_base_strutture AS map_costo
            ON map_costo.strutture_id = sa.id AND sa.id = :difesaId
            """, nativeQuery = true)
    List<SviluppoCostiProjection> getCostiSviluppo(Long playerId, Long difesaId);

    @Query(value = """
            SELECT *
            FROM player_difesa
            WHERE player_difesa.player_id = :playerId AND player_difesa.difesa_id = :difesaId
            """, nativeQuery = true)
    Optional<PlayerDifesa> findByPlayerIdAndSviluppoId(Long playerId, Long difesaId);
}
