package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerBasicInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicRepository extends JpaRepository<PlayerBasicInformationEntity, Long> {

}
