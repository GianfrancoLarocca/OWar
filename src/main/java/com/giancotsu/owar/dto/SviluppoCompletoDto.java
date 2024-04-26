package com.giancotsu.owar.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SviluppoCompletoDto {

    private long id;
    private String nome;
    private String descrizione;
    private Integer livello;
    private String urlImmagine;
    private Map<String, Double> costi;
    private Map<String, Double> produzioneAttuale;
    private Map<String, Double> produzioneProssimoLivello;
    private String chance;

    public SviluppoCompletoDto(String nome, String descrizione, Integer livello, String urlImmagine, Map<String, Double> costi, Map<String, Double> produzioneAttuale, Map<String, Double> produzioneProssimoLivello) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.livello = livello;
        this.urlImmagine = urlImmagine;
        this.costi = costi;
        this.produzioneAttuale = produzioneAttuale;
        this.produzioneProssimoLivello = produzioneProssimoLivello;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getLivello() {
        return livello;
    }

    public void setLivello(Integer livello) {
        this.livello = livello;
    }

    public String getUrlImmagine() {
        return urlImmagine;
    }

    public void setUrlImmagine(String urlImmagine) {
        this.urlImmagine = urlImmagine;
    }

    public Map<String, String> getCosti() {
        Map<String, String> newMap = new HashMap<>();
        costi.forEach((nome, costo) -> {
            newMap.put(nome, String.format(Locale.ITALY, "%.0f", costo));
        });

        return newMap;
    }

    public void setCosti(Map<String, Double> costi) {
        this.costi = costi;
    }

    public Map<String, String> getProduzioneAttuale() {
        Map<String, String> newMap = new HashMap<>();
        produzioneAttuale.forEach((nome, costo) -> {
            newMap.put(nome, String.format(Locale.ITALY, "%.0f", costo));
        });

        return newMap;
    }

    public void setProduzioneAttuale(Map<String, Double> produzioneAttuale) {
        this.produzioneAttuale = produzioneAttuale;
    }

    public Map<String, String> getProduzioneProssimoLivello() {
        Map<String, String> newMap = new HashMap<>();
        produzioneProssimoLivello.forEach((nome, costo) -> {
            newMap.put(nome, String.format(Locale.ITALY, "%.0f", costo));
        });

        return newMap;
    }

    public void setProduzioneProssimoLivello(Map<String, Double> produzioneProssimoLivello) {
        this.produzioneProssimoLivello = produzioneProssimoLivello;
    }

    public String getChance() {
        return chance;
    }

    public void setChance(String chance) {
        this.chance = chance;
    }
}
