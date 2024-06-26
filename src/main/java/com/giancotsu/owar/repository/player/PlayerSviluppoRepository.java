package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.entity.player.PlayerSviluppo;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import com.giancotsu.owar.projection.SviluppoProduzioneRisorseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerSviluppoRepository extends JpaRepository<PlayerSviluppo, Long> {

    @Query(value = """
            SELECT *
            FROM player_sviluppo
            WHERE player_sviluppo.player_id = :playerId AND player_sviluppo.sviluppo_id = :sviluppoId
            """, nativeQuery = true)
    Optional<PlayerSviluppo> findByPlayerIdAndSviluppoId(Long playerId, Long sviluppoId);

    @Query(value = """
            SELECT livello, moltiplicatore_costo AS moltiplicatore, map_costo_base.costo_base as costo, map_costo_base.costo_base_key as risorsa
            FROM player_sviluppo
            INNER JOIN sviluppo
            ON sviluppo.id = player_sviluppo.sviluppo_id AND player_sviluppo.player_id = :playerId
            INNER JOIN map_costo_base
            ON map_costo_base.sviluppo_id = sviluppo.id AND sviluppo.id = :sviluppoId
            """, nativeQuery = true)
    List<SviluppoCostiProjection> getCostiSviluppo(Long playerId, Long sviluppoId);

    @Query(value = """
            SELECT map_produzione_risorse.produzione_risorse_key AS risorsa, map_produzione_risorse.produzione_risorse AS produzione, sviluppo.moltiplicatore_produzione_risorse AS moltiplicatore, player_sviluppo.livello
            FROM player_sviluppo
            INNER JOIN sviluppo
            ON sviluppo.id = player_sviluppo.sviluppo_id AND player_sviluppo.player_id = :playerId
            INNER JOIN map_produzione_risorse
            ON map_produzione_risorse.sviluppo_id = sviluppo.id AND sviluppo.id = :sviluppoId
            """, nativeQuery = true)
    List<SviluppoProduzioneRisorseProjection> getProduzioneRisorseSviluppo(Long playerId, Long sviluppoId);

    @Query(value = """
            SELECT map_produzione_risorse.produzione_risorse_key AS risorsa, map_produzione_risorse.produzione_risorse AS produzione, sviluppo.moltiplicatore_produzione_risorse AS moltiplicatore, player_sviluppo.livello
            FROM player_sviluppo
            INNER JOIN sviluppo
            ON sviluppo.id = player_sviluppo.sviluppo_id AND player_sviluppo.player_id = :playerId
            INNER JOIN map_produzione_risorse
            ON map_produzione_risorse.sviluppo_id = sviluppo.id
            """, nativeQuery = true)
    List<SviluppoProduzioneRisorseProjection> getProduzioneRisorse(Long playerId);

    @Query(value = """
            SELECT pl.livello, s.nome, s.url_immagine AS url
            FROM player_sviluppo AS pl
            INNER JOIN sviluppo AS s
            ON s.id = pl.sviluppo_id AND pl.player_id = :playerId
            """, nativeQuery = true)
    List<BasicBuildingInfoProjection> getAllBasicBuildingsInfo(Long playerId);
}
