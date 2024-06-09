package com.giancotsu.owar.entity.player.sviluppo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.giancotsu.owar.entity.player.PlayerStrutture;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Strutture extends SviluppoAstratto {

    private Double effectValue;

    @OneToMany(mappedBy = "strutture", cascade = {CascadeType.ALL})
    @JsonManagedReference
    @JsonIgnore
    private Set<PlayerStrutture> playerStrutture;

    public Strutture(Long id, String nome, String descrizione, String urlImmagine, Double moltiplicatoreCosto, Map<RisorseEnum, Double> costoBase, Double effectValue) {
        super(id, nome, descrizione, urlImmagine, moltiplicatoreCosto, costoBase);
        this.effectValue = effectValue;
    }

}
