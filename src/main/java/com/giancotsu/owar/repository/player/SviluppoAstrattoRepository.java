package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.sviluppo.SviluppoAstratto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SviluppoAstrattoRepository extends JpaRepository<SviluppoAstratto, Long> {

    @Query(value = """
            SELECT nome
            FROM sviluppo_astratto
            WHERE sviluppo_astratto.id = :id
            """, nativeQuery = true)
    Optional<String> getNameById(Long id);
}
