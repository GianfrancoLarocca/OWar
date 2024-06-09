package com.giancotsu.owar.event.strutture;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerStrutture;
import org.springframework.context.ApplicationEvent;

public class SviluppoStruttureTryLvlUpSuccessEvent extends ApplicationEvent {

    private PlayerEntity player;
    private PlayerStrutture ps;
    private Double exp;

    public SviluppoStruttureTryLvlUpSuccessEvent(Object source) {
        super(source);
    }

    public SviluppoStruttureTryLvlUpSuccessEvent(Object source, PlayerEntity player, PlayerStrutture ps, Double exp) {
        super(source);
        this.player = player;
        this.ps = ps;
        this.exp = exp;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public PlayerStrutture getPs() {
        return ps;
    }

    public Double getExp() {
        return exp;
    }
}
