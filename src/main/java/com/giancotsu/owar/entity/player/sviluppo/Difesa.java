package com.giancotsu.owar.entity.player.sviluppo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.giancotsu.owar.entity.player.PlayerDifesa;
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
public class Difesa extends SviluppoAstratto {

    private int livelloFabbricaRequisito;

    private long danno;
    private long penetrazioneArmatura;
    private long armatura;
    private long vita;
    private long numeroMassimoObbiettivi;
    private long numeroMassimoDifesa;

    private long dannoNextLvl;
    private long penetrazioneArmaturaNextLvl;
    private long armaturaNextLvl;
    private long vitaNextLvl;
    private long numeroMassimoObbiettiviNextLvl;
    private long numeroMassimoDifesaNextLvl;

    @OneToMany(mappedBy = "difesa", cascade = {CascadeType.ALL})
    @JsonManagedReference
    @JsonIgnore
    private Set<PlayerDifesa> playerDifesa;

    public Difesa(Long id, String nome, String descrizione, String urlImmagine, Double moltiplicatoreCosto, Map<RisorseEnum, Double> costoBase, int livelloFabbricaRequisito, long danno, long penetrazioneArmatura, long armatura, long vita, long numeroMassimoObbiettivi, long numeroMassimoDifesa, long dannoNextLvl, long penetrazioneArmaturaNextLvl, long armaturaNextLvl, long vitaNextLvl, long numeroMassimoObbiettiviNextLvl, long numeroMassimoDifesaNextLvl) {
        super(id, nome, descrizione, urlImmagine, moltiplicatoreCosto, costoBase);
        this.livelloFabbricaRequisito = livelloFabbricaRequisito;
        this.danno = danno;
        this.penetrazioneArmatura = penetrazioneArmatura;
        this.armatura = armatura;
        this.vita = vita;
        this.numeroMassimoObbiettivi = numeroMassimoObbiettivi;
        this.numeroMassimoDifesa = numeroMassimoDifesa;
        this.dannoNextLvl = dannoNextLvl;
        this.penetrazioneArmaturaNextLvl = penetrazioneArmaturaNextLvl;
        this.armaturaNextLvl = armaturaNextLvl;
        this.vitaNextLvl = vitaNextLvl;
        this.numeroMassimoObbiettiviNextLvl = numeroMassimoObbiettiviNextLvl;
        this.numeroMassimoDifesaNextLvl = numeroMassimoDifesaNextLvl;
    }
}
