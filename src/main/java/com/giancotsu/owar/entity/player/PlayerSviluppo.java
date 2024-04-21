package com.giancotsu.owar.entity.player;

import com.giancotsu.owar.entity.player.sviluppo.Sviluppo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
public class PlayerSviluppo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer livello = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sviluppo_id")
    private Sviluppo sviluppo;
}
