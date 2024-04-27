package com.giancotsu.owar.event.sviluppo;

import com.giancotsu.owar.entity.player.PlayerSviluppo;
import org.springframework.context.ApplicationEvent;

public class SviluppoUpEvent extends ApplicationEvent {

    private PlayerSviluppo ps;
    public SviluppoUpEvent(Object source, PlayerSviluppo ps) {
        super(source);
        this.ps = ps;
    }

    public PlayerSviluppo getPs() {
        return ps;
    }
}
