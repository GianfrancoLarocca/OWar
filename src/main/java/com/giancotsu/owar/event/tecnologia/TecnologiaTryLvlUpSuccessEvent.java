package com.giancotsu.owar.event.tecnologia;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerTecnologia;
import org.springframework.context.ApplicationEvent;

public class TecnologiaTryLvlUpSuccessEvent extends ApplicationEvent {

    private PlayerEntity player;
    private PlayerTecnologia pt;
    private Double exp;

    public TecnologiaTryLvlUpSuccessEvent(Object source) {
        super(source);
    }

    public TecnologiaTryLvlUpSuccessEvent(Object source, PlayerEntity player, PlayerTecnologia pt, Double exp) {
        super(source);
        this.player = player;
        this.pt = pt;
        this.exp = exp;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public PlayerTecnologia getPs() {
        return pt;
    }

    public Double getExp() {
        return exp;
    }
}
