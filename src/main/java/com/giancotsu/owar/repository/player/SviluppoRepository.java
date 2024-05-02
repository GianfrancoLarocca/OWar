package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.sviluppo.Sviluppo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SviluppoRepository extends JpaRepository<Sviluppo, Long> {

    Optional<Sviluppo> findByNome(String name);

    @Query(value = """
            SELECT sviluppo.nome
            FROM sviluppo
            WHERE sviluppo.id = :id
            """, nativeQuery = true)
    Optional<String> getNameById(Long id);
}
