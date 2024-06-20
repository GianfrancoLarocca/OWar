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
public class SviluppoDifesaDettagliDto {

    private long id;
    private String nome;
    private String descrizione;
    private Integer livello;
    private String urlImmagine;
    private Map<String, Double> costi;
    private String chance;
    private int livelloFabbricaRequisito;

    private long danno;
    private long penetrazioneArmatura;
    private long armatura;
    private long vita;

    public SviluppoDifesaDettagliDto(String nome, String descrizione, Integer livello, String urlImmagine, Map<String, Double> costi, String chance, int livelloFabbricaRequisito, long danno, long penetrazioneArmatura, long armatura, long vita) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.livello = livello;
        this.urlImmagine = urlImmagine;
        this.costi = costi;
        this.chance = chance;
        this.livelloFabbricaRequisito = livelloFabbricaRequisito;
        this.danno = danno;
        this.penetrazioneArmatura = penetrazioneArmatura;
        this.armatura = armatura;
        this.vita = vita;
    }

    public Map<String, String> getCosti() {
        Map<String, String> newMap = new HashMap<>();
        costi.forEach((nome, costo) -> {
            newMap.put(nome, String.format(Locale.ITALY, "%.0f", costo));
        });

        return newMap;
    }
}
