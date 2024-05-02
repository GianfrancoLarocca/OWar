package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "player")
@Data
@ToString
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isOnline = false;
    private boolean isBanned = false;
    private boolean isVacationed = false;

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private PlayerBasicInformationEntity basicInformation;

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private RegistroAttivita registroAttivita;

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private PlayerRisorse playerRisorse;

    @OneToMany(mappedBy = "player", cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<PlayerSviluppo> playerSviluppo;

    public PlayerEntity() {
        this.playerRisorse = new PlayerRisorse();
        this.registroAttivita = new RegistroAttivita();
    }


}
