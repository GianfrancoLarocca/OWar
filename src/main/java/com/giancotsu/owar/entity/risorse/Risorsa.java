package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@ToString
public abstract class Risorsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String unitaMisura;
    private Long quantita;
    private String urlImmagine;



    protected Risorsa() {
    }

    public Risorsa(String nome, String unitaMisura, Long quantita, String urlImmagine) {
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
