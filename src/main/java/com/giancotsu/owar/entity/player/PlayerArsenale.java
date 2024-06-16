package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.giancotsu.owar.entity.player.sviluppo.Arsenale;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlayerArsenale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer livello = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private PlayerEntity player;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "arsenale_id")
    @JsonIgnore
    private Arsenale arsenale;
}
