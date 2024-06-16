package com.giancotsu.owar.event.arsenale;

import com.giancotsu.owar.entity.player.PlayerArsenale;
import com.giancotsu.owar.entity.player.PlayerEntity;
import org.springframework.context.ApplicationEvent;

public class ArsenaleTryLvlUpFailEvent extends ApplicationEvent {

    private PlayerEntity player;
    private PlayerArsenale playerArsenale;

    public ArsenaleTryLvlUpFailEvent(Object source, PlayerEntity player, PlayerArsenale playerArsenale) {
        super(source);
        this.player = player;
        this.playerArsenale = playerArsenale;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public PlayerArsenale getPlayerArsenale() {
        return playerArsenale;
    }
}
