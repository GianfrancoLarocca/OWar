package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class UnitaRimanentiCoda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private long idUnita;
    private long quantita;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private PlayerEntity player;

}
