package com.giancotsu.owar.event.sviluppo;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerSviluppo;
import org.springframework.context.ApplicationEvent;

public class SviluppoTryLvlUpFailEvent extends ApplicationEvent {

    private PlayerSviluppo ps;
    private PlayerEntity player;

    public SviluppoTryLvlUpFailEvent(Object source, PlayerEntity player, PlayerSviluppo ps) {
        super(source);
        this.ps = ps;
        this.player = player;
    }

    public SviluppoTryLvlUpFailEvent(Object source) {
        super(source);
    }

    public PlayerSviluppo getPs() {
        return ps;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
