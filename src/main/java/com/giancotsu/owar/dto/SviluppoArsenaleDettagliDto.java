package com.giancotsu.owar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SviluppoArsenaleDettagliDto {

    private long id;
    private String nome;
    private String descrizione;
    private Integer livello;
    private String urlImmagine;
    private Map<String, Double> costi;
    private String chance;
    private int livelloFabbricaRequisito;

    private long attacco;
    private long armatura;
    private long vita;
    private long velocita;
    private long stiva;
    private long consumo;

    public SviluppoArsenaleDettagliDto(String nome, String descrizione, Integer livello, String urlImmagine, Map<String, Double> costi, String chance, int livelloFabbricaRequisito, long attacco, long armatura, long vita, long velocita, long stiva, long consumo) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.livello = livello;
        this.urlImmagine = urlImmagine;
        this.costi = costi;
        this.chance = chance;
        this.livelloFabbricaRequisito = livelloFabbricaRequisito;
        this.attacco = attacco;
        this.armatura = armatura;
        this.vita = vita;
        this.velocita = velocita;
        this.stiva = stiva;
        this.consumo = consumo;
    }

    public Map<String, String> getCosti() {
        Map<String, String> newMap = new HashMap<>();
        costi.forEach((nome, costo) -> {
            newMap.put(nome, String.format(Locale.ITALY, "%.0f", costo));
        });

        return newMap;
    }
}
