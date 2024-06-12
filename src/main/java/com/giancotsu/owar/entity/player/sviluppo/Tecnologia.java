package com.giancotsu.owar.entity.player.sviluppo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.giancotsu.owar.entity.player.PlayerTecnologia;
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
public class Tecnologia extends SviluppoAstratto{

    private Double effectValue;
    private String effectValueType;
    private int livelloLaboratorioRequisito;

    @OneToMany(mappedBy = "tecnologia", cascade = {CascadeType.ALL})
    @JsonManagedReference
    @JsonIgnore
    private Set<PlayerTecnologia> playerTecnologia;

    public Tecnologia(Long id, String nome, String descrizione, String urlImmagine, Double moltiplicatoreCosto, Map<RisorseEnum, Double> costoBase, Double effectValue, String effectValueType, int livelloLaboratorioRequisito) {
        super(id, nome, descrizione, urlImmagine, moltiplicatoreCosto, costoBase);
        this.effectValue = effectValue;
        this.livelloLaboratorioRequisito = livelloLaboratorioRequisito;
        this.effectValueType = effectValueType;
    }

}
