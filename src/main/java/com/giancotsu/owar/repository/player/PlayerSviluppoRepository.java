package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerSviluppo;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import com.giancotsu.owar.projection.SviluppoCrescitaRisorseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerSviluppoRepository extends JpaRepository<PlayerSviluppo, Long> {

    @Query(value = """
            SELECT livello, moltiplicatore_costo AS moltiplicatore, map_costo_base.costo_base as costo, map_costo_base.costo_base_key as risorsa
            FROM "player-sviluppo"
            INNER JOIN sviluppo
            ON sviluppo.id = "player-sviluppo".sviluppo_id AND "player-sviluppo".player_id = :playerId
            INNER JOIN map_costo_base
            ON map_costo_base.sviluppo_id = sviluppo.id AND sviluppo.id = :sviluppoId
            """, nativeQuery = true)
    List<SviluppoCostiProjection> getCostiSviluppo(Long playerId, Long sviluppoId);

    @Query(value = """
            SELECT map_crescita_risorse.crescita_risorse_key AS risorsa, map_crescita_risorse.crescita_risorse AS crescita, sviluppo.moltiplicatore_crescita_risorse AS moltiplicatore
            FROM "player-sviluppo"
            INNER JOIN sviluppo
            ON sviluppo.id = "player-sviluppo".sviluppo_id AND "player-sviluppo".player_id = :playerId
            INNER JOIN map_crescita_risorse
            ON map_crescita_risorse.sviluppo_id = sviluppo.id AND sviluppo.id = :sviluppoId
            """, nativeQuery = true)
    List<SviluppoCrescitaRisorseProjection> getCrescitaRisorseSviluppo(Long playerId, Long sviluppoId);

    @Query(value = """
            SELECT map_crescita_risorse.crescita_risorse_key AS risorsa, map_crescita_risorse.crescita_risorse AS crescita, sviluppo.moltiplicatore_crescita_risorse AS moltiplicatore, "player-sviluppo".livello
            FROM "player-sviluppo"
            INNER JOIN sviluppo
            ON sviluppo.id = "player-sviluppo".sviluppo_id AND "player-sviluppo".player_id = :playerId
            INNER JOIN map_crescita_risorse
            ON map_crescita_risorse.sviluppo_id = sviluppo.id
            """, nativeQuery = true)
    List<SviluppoCrescitaRisorseProjection> getCrescitaRisorse(Long playerId);
}
