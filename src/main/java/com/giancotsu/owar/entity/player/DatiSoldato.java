package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatiSoldato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(name = "descrizione", length = 2048)
    private String descrizione;
    private String urlImmagine;

    private int secondiPerAddestrare;
    private String armaSoldato;

    @ElementCollection
    @CollectionTable(name = "map_costo_base_soldati", joinColumns = @JoinColumn(name = "soldato_id"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<RisorseEnum, Double> costoBaseEsercito;

    @OneToMany(mappedBy = "datiSoldato", cascade = {CascadeType.ALL})
    @JsonManagedReference
    @JsonIgnore
    private Set<Soldato> soldati;

    public DatiSoldato(String nome, String descrizione, String urlImmagine, int secondiPerAddestrare, Map<RisorseEnum, Double> costoBase, String armaSoldato) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.urlImmagine = urlImmagine;
        this.secondiPerAddestrare = secondiPerAddestrare;
        this.costoBaseEsercito = costoBase;
        this.armaSoldato = armaSoldato;
    }
}
