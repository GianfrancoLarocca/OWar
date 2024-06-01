package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
