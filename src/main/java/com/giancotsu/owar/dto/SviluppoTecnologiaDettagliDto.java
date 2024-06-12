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
public class SviluppoTecnologiaDettagliDto {

    private long id;
    private String nome;
    private String descrizione;
    private Integer livello;
    private String urlImmagine;
    private Map<String, Double> costi;
    private String chance;
    private Double effectValue;
    private Double nextEffectValue;
    private String effectValueType;
    private int livelloLaboratorioRequisito;

    public SviluppoTecnologiaDettagliDto(String nome, String descrizione, Integer livello, String urlImmagine, Map<String, Double> costi, Double effectValue, int livelloLaboratorioRequisito) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.livello = livello;
        this.urlImmagine = urlImmagine;
        this.costi = costi;
        this.effectValue = effectValue;
        this.livelloLaboratorioRequisito = livelloLaboratorioRequisito;
    }

    public Map<String, String> getCosti() {
        Map<String, String> newMap = new HashMap<>();
        costi.forEach((nome, costo) -> {
            newMap.put(nome, String.format(Locale.ITALY, "%.0f", costo));
        });

        return newMap;
    }
}
