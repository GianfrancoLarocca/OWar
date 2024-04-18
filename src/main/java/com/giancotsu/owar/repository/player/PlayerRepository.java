package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

}
