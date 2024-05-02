package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.Attivita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

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
    Optional<List<Attivita>> getAttivitaByPlayerId(Long playerId);
}
