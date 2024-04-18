package com.giancotsu.owar.entity.player;

import com.giancotsu.owar.entity.risorse.*;
import com.giancotsu.owar.entity.risorse.Risorsa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "player-risorse")
@Data
@NoArgsConstructor
@ToString
public class PlayerRisorse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Risorsa metallo = new Metallo();
    private Risorsa bitcoin = new Bitcoin();
    private Risorsa Acqua = new Acqua();
    private Risorsa Civili = new Civili();
    private Risorsa Energia = new Energia();
    private Risorsa Microchip = new Microchip();

}
