package com.giancotsu.owar.event.difesa;

import com.giancotsu.owar.entity.player.PlayerDifesa;
import com.giancotsu.owar.entity.player.PlayerEntity;
import org.springframework.context.ApplicationEvent;

public class DifesaTryLvlUpFailEvent extends ApplicationEvent {

    private PlayerEntity player;
    private PlayerDifesa playerDifesa;

    public DifesaTryLvlUpFailEvent(Object source, PlayerEntity player, PlayerDifesa playerDifesa) {
        super(source);
        this.player = player;
        this.playerDifesa = playerDifesa;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public PlayerDifesa getPlayerDifesa() {
        return playerDifesa;
    }
}
