package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.UnitaRimanentiCoda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UnitaRimanentiCodaRepository extends JpaRepository<UnitaRimanentiCoda, Long> {

    @Query(value = """
            SELECT *
            FROM unita_rimanenti_coda AS urc
            WHERE urc.player_id = :playerId AND urc.tipo LIKE :tipo
            """, nativeQuery = true)
    Optional<List<UnitaRimanentiCoda>> findByPlayerId(long playerId, String tipo);


    @Query(value = """
            SELECT *
            FROM unita_rimanenti_coda AS urc
            WHERE urc.player_id = :playerId AND urc.id_unita = :unitaId AND urc.tipo LIKE :tipo
            """, nativeQuery = true)
    Optional<UnitaRimanentiCoda> findByPlayerIdAndUnitaId(long playerId, long unitaId, String tipo);

    @Modifying
    @Transactional
    @Query("DELETE FROM UnitaRimanentiCoda urc WHERE urc.player.id = :playerId AND urc.idUnita = :unitaId AND urc.tipo LIKE :tipo")
    void deleteByPlayerIdAndUnitaIdAndTipo(@Param("playerId") long playerId, @Param("unitaId") long unitaId, @Param("tipo") String tipo);

}