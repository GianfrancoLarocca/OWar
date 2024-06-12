package com.giancotsu.owar.event.tecnologia;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerTecnologia;
import org.springframework.context.ApplicationEvent;

public class TecnologiaTryLvlUpFailEvent extends ApplicationEvent {

    private PlayerEntity player;
    private PlayerTecnologia pt;
    public TecnologiaTryLvlUpFailEvent(Object source, PlayerEntity p, PlayerTecnologia pt) {
        super(source);
        this.player = p;
        this.pt = pt;
    }

    public TecnologiaTryLvlUpFailEvent(Object source) {
        super(source);
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public PlayerTecnologia getPs() {
        return pt;
    }
}
