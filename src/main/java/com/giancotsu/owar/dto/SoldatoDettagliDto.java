package com.giancotsu.owar.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Data
public class SoldatoDettagliDto {

    private long id;
    private String nome;
    private String descrizione;
    private long quantitaSoldati;
    private String urlImmagine;
    private Map<String, Double> costi;
    private long secondiPerAddestrare;

    private String armaSoldato;
    private long limiteUnita;

    public Map<String, String> getCosti() {
        Map<String, String> newMap = new HashMap<>();
        costi.forEach((nome, costo) -> {
            newMap.put(nome, String.format(Locale.ITALY, "%.0f", costo));
        });

        return newMap;
    }
}
