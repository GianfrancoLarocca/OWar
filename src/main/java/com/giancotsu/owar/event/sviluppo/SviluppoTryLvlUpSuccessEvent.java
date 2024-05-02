package com.giancotsu.owar.event.sviluppo;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerSviluppo;
import org.springframework.context.ApplicationEvent;

public class SviluppoTryLvlUpSuccessEvent extends ApplicationEvent {

    private PlayerSviluppo ps;
    private PlayerEntity player;
    public SviluppoTryLvlUpSuccessEvent(Object source, PlayerEntity player, PlayerSviluppo ps) {
        super(source);
        this.ps = ps;
        this.player = player;
    }

    public PlayerSviluppo getPs() {
        return ps;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
