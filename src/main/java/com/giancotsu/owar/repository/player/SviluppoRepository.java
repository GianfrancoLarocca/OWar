package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.sviluppo.Sviluppo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SviluppoRepository extends JpaRepository<Sviluppo, Long> {

    Optional<Sviluppo> findByNome(String name);
}
