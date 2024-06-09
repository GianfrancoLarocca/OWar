package com.giancotsu.owar.event.strutture;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerStrutture;
import org.springframework.context.ApplicationEvent;

public class SviluppoStruttureTryLvlUpFailEvent extends ApplicationEvent {

    private PlayerEntity player;
    private PlayerStrutture ps;
    public SviluppoStruttureTryLvlUpFailEvent(Object source, PlayerEntity p, PlayerStrutture ps) {
        super(source);
        this.player = p;
        this.ps = ps;
    }

    public SviluppoStruttureTryLvlUpFailEvent(Object source) {
        super(source);
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public PlayerStrutture getPs() {
        return ps;
    }
}
