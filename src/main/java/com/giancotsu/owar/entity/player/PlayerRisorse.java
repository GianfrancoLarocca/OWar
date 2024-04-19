package com.giancotsu.owar.entity.player;

import com.giancotsu.owar.entity.risorse.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
public class PlayerRisorse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private Metallo metallo = new Metallo();

    @OneToOne(cascade = {CascadeType.ALL})
    private Bitcoin bitcoin = new Bitcoin();

    @OneToOne(cascade = {CascadeType.ALL})
    private Acqua Acqua = new Acqua();

    @OneToOne(cascade = {CascadeType.ALL})
    private Civili Civili = new Civili();

    @OneToOne(cascade = {CascadeType.ALL})
    private Energia Energia = new Energia();

    @OneToOne(cascade = {CascadeType.ALL})
    private Microchip Microchip = new Microchip();

}
