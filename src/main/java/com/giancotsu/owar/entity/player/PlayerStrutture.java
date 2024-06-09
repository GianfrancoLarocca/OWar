package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.giancotsu.owar.entity.player.sviluppo.Strutture;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlayerStrutture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer livello = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private PlayerEntity player;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "strutture_id")
    @JsonIgnore
    private Strutture strutture;
}
