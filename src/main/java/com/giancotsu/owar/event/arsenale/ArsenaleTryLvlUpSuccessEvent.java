package com.giancotsu.owar.event.arsenale;

import com.giancotsu.owar.entity.player.PlayerArsenale;
import com.giancotsu.owar.entity.player.PlayerEntity;
import org.springframework.context.ApplicationEvent;

public class ArsenaleTryLvlUpSuccessEvent extends ApplicationEvent {

    private PlayerEntity player;
    private PlayerArsenale playerArsenale;
    private Double exp;

    public ArsenaleTryLvlUpSuccessEvent(Object source, PlayerEntity player, PlayerArsenale playerArsenale, Double exp) {
        super(source);
        this.player = player;
        this.playerArsenale = playerArsenale;
        this.exp = exp;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public PlayerArsenale getPlayerArsenale() {
        return playerArsenale;
    }

    public Double getExp() {
        return exp;
    }
}
