package com.giancotsu.owar.entity.player;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Contatori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int notificationCounter;
    private int chatCounter;

    public void incrementaContatoreNotifiche() {
        this.notificationCounter++;
    }
}
