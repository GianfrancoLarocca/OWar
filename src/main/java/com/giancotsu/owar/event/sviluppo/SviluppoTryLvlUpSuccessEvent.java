package com.giancotsu.owar.event.sviluppo;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerSviluppo;
import org.springframework.context.ApplicationEvent;

public class SviluppoTryLvlUpSuccessEvent extends ApplicationEvent {

    private PlayerSviluppo ps;
    private PlayerEntity player;
    private Double exp;

    public SviluppoTryLvlUpSuccessEvent(Object source, PlayerEntity player, PlayerSviluppo ps, Double exp) {
        super(source);
        this.ps = ps;
        this.player = player;
        this.exp = exp;
    }

    public PlayerSviluppo getPs() {
        return ps;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public Double getExp() {
        return exp;
    }
}
