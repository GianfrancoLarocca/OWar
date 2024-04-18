package com.giancotsu.owar.entity.risorse;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Risorsa implements Serializable {

    private String nome;
    private String unitaMisura;
    private Long quantita;
    private String urlImmagine;

    private Double incrementoRisorsa = 0D;

    protected Risorsa() {
    }

    protected Risorsa(String nome, String unitaMisura, Long quantita, String urlImmagine) {
        this.nome = nome;
        this.unitaMisura = unitaMisura;
        this.quantita = quantita;
        this.urlImmagine = urlImmagine;
    }

    public String valueShort() {
        String quantitaShort = quantita.toString();
        if(quantita>999 && quantita<1000000) {
            quantitaShort = String.format("%.1f %s", (quantita/1000.0), "k");
        } else if(quantita>999999 && quantita<1000000000) {
            quantitaShort = String.format("%.3f %s", (quantita/1000000.0), "M");
        } else if (quantita>999999999) {
            quantitaShort = String.format("%.3f %s", (quantita/1000000000.0), "Bn");
        }

        return "%s %s".formatted(quantitaShort, unitaMisura);
    }

    public String valueShorter() {
        String quantitaShort = quantita.toString();
        if(quantita>999 && quantita<1000000) {
            quantitaShort = String.format("%.1f %s", (quantita/1000.0), "k");
        } else if(quantita>999999 && quantita<1000000000) {
            quantitaShort = String.format("%.3f %s", (quantita/1000000.0), "M");
        } else if (quantita>999999999) {
            quantitaShort = String.format("%.3f %s", (quantita/1000000000.0), "Bn");
        }
        return "%s".formatted(quantitaShort);
    }

    public String extendedValue() {
        return "%d %s".formatted(quantita, unitaMisura);
    }
}
