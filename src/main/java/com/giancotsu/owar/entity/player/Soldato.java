package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Soldato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long quantitaSoldati;
    private int secondiPerAddestrare;
    private long quantitaSoldatiCoda;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private PlayerEntity player;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dati_soldato_id")
    @JsonIgnore
    private DatiSoldato datiSoldato;

}
