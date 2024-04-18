package com.giancotsu.owar.entity.player.sviluppo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.giancotsu.owar.entity.player.PlayerSviluppo;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Entity
@Data
public class Sviluppo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(name = "descrizione", length = 2048)
    private String descrizione;
    private String urlImmagine;

    private Long moltiplicatoreCosto = 2L;

    @ElementCollection
    @CollectionTable(name = "map_costo_base", joinColumns = @JoinColumn(name = "sviluppo_id"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<RisorseEnum, Long> costoBase;

    @OneToMany(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Set<PlayerSviluppo> playerSviluppo;

}
