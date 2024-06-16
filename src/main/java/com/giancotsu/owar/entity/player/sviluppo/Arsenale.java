package com.giancotsu.owar.entity.player.sviluppo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.giancotsu.owar.entity.player.PlayerArsenale;
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
public class Arsenale extends SviluppoAstratto {

    private int livelloFabbricaRequisito;

    private long attacco;
    private long armatura;
    private long vita;
    private long velocita;
    private long stiva;
    private long consumo;

    @OneToMany(mappedBy = "arsenale", cascade = {CascadeType.ALL})
    @JsonManagedReference
    @JsonIgnore
    private Set<PlayerArsenale> playerArsenale;

    public Arsenale(Long id, String nome, String descrizione, String urlImmagine, Double moltiplicatoreCosto, Map<RisorseEnum, Double> costoBase, int livelloFabbricaRequisito, long attacco, long armatura, long vita, long velocita, long stiva, long consumo) {
        super(id, nome, descrizione, urlImmagine, moltiplicatoreCosto, costoBase);
        this.livelloFabbricaRequisito = livelloFabbricaRequisito;
        this.attacco = attacco;
        this.armatura = armatura;
        this.vita = vita;
        this.velocita = velocita;
        this.stiva = stiva;
        this.consumo = consumo;
    }

}
