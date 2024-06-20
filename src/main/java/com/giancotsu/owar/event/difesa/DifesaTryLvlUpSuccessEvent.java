package com.giancotsu.owar.event.difesa;

import com.giancotsu.owar.entity.player.PlayerDifesa;
import com.giancotsu.owar.entity.player.PlayerEntity;
import org.springframework.context.ApplicationEvent;

public class DifesaTryLvlUpSuccessEvent extends ApplicationEvent {
    private PlayerEntity player;
    private PlayerDifesa playerDifesa;
    private Double exp;

    public DifesaTryLvlUpSuccessEvent(Object source, PlayerEntity player, PlayerDifesa playerDifesa, Double exp) {
        super(source);
        this.player = player;
        this.playerDifesa = playerDifesa;
        this.exp = exp;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public PlayerDifesa getPlayerDifesa() {
        return playerDifesa;
    }

    public Double getExp() {
        return exp;
    }
}
