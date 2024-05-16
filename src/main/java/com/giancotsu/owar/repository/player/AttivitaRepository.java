package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.Attivita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttivitaRepository extends JpaRepository<Attivita, Long> {

    @Query(value = """
            SELECT attivita.id, attivita."data", attivita.azione, attivita.descrizione, attivita.registro_attivita_id
            FROM player
            INNER JOIN registro_attivita
            ON player.registro_attivita_id = registro_attivita.id AND player.id = :playerId
            INNER JOIN attivita
            ON attivita.registro_attivita_id = registro_attivita.id
            ORDER BY attivita."data" DESC
            """, nativeQuery = true)
    List<Attivita> getAttivitaByPlayerId(Long playerId);

    @Query(value = """
        SELECT attivita.id, attivita."data", attivita.azione, attivita.descrizione, attivita.registro_attivita_id
        FROM player
        INNER JOIN registro_attivita
        ON player.registro_attivita_id = registro_attivita.id AND player.id = :playerId
        INNER JOIN attivita
        ON attivita.registro_attivita_id = registro_attivita.id
        ORDER BY attivita."data" DESC
        """, nativeQuery = true)
    Page<Attivita> getAttivitaByPlayerIdPageable(@Param("playerId") Long playerId, Pageable pageable);
}
