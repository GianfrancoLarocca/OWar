package com.giancotsu.owar.entity.player;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "basic_information")
@NoArgsConstructor
@ToString
public class PlayerBasicInformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname",nullable = false, unique = true)
    private String nickname;

    @Column(name = "lvl", nullable = false)
    private int livello = 0;

    private String urlImmagine = "../../assets/img/player/user.png";
    private LocalDateTime dataRegistrazione;

    //EXP
    //Exp(n) = Base * Math.pow(Fattore, livello);
    private Double exp = 0.0;
    private Double expTot = 0.0;
    private Double expBase = 2000.5;
    private Double expFactor = 1.5;
    private Double expStartLvl = 0.0;
    private Double expNextLevel = expBase * Math.pow(expFactor, livello);

    //PUNTI
    private long cp = 0;
    private long puntiAttacco = 0;
    private long puntiDifesa = 0;

    //SLOT CODA
    private int slotCodaEsercito = 3;
    private int slotCodaDifesa = 3;


    public PlayerBasicInformationEntity(String nickname) {
        this.nickname = nickname;
    }

    public PlayerBasicInformationEntity(String nickname, LocalDateTime dataRegistrazione) {
        this.nickname = nickname;
        this.dataRegistrazione = dataRegistrazione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public String getUrlImmagine() {
        return urlImmagine;
    }

    public void setUrlImmagine(String urlImmagine) {
        this.urlImmagine = urlImmagine;
    }

    public String getDataRegistrazione() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dataRegistrazione.format(formatter);
    }

    public void setDataRegistrazione(LocalDateTime dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public Double getExp() {
        return exp;
    }

    public void setExp(Double exp) {
        this.exp = exp;
    }

    public Double getExpTot() {
        return expTot;
    }

    public void setExpTot(Double expTot) {
        this.expTot = expTot;
    }

    public Double getExpBase() {
        return expBase;
    }

    public void setExpBase(Double expBase) {
        this.expBase = expBase;
    }

    public Double getExpFactor() {
        return expFactor;
    }

    public void setExpFactor(Double expFactor) {
        this.expFactor = expFactor;
    }

    public Double getExpStartLvl() {
        return expStartLvl;
    }

    public void setExpStartLvl(Double expStartLvl) {
        this.expStartLvl = expStartLvl;
    }

    public Double getExpNextLevel() {
        return expNextLevel;
    }

    public void setExpNextLevel(Double expNextLevel) {
        this.expNextLevel = expNextLevel;
    }

    public long getCp() {
        return cp;
    }

    public void setCp(long cp) {
        this.cp = cp;
    }

    public long getPuntiAttacco() {
        return puntiAttacco;
    }

    public void setPuntiAttacco(long puntiAttacco) {
        this.puntiAttacco = puntiAttacco;
    }

    public long getPuntiDifesa() {
        return puntiDifesa;
    }

    public void setPuntiDifesa(long puntiDifesa) {
        this.puntiDifesa = puntiDifesa;
    }

    public int getSlotCodaEsercito() {
        return slotCodaEsercito;
    }

    public void setSlotCodaEsercito(int slotCodaEsercito) {
        this.slotCodaEsercito = slotCodaEsercito;
    }

    public int getSlotCodaDifesa() {
        return slotCodaDifesa;
    }

    public void setSlotCodaDifesa(int slotCodaDifesa) {
        this.slotCodaDifesa = slotCodaDifesa;
    }
}
