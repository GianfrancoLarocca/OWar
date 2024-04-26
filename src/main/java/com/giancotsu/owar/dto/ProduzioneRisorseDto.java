package com.giancotsu.owar.dto;

import lombok.AllArgsConstructor;

import java.util.Locale;

@AllArgsConstructor
public class ProduzioneRisorseDto {

    private String risorsa;
    private Double produzione;

    public String getRisorsa() {
        return risorsa;
    }

    public void setRisorsa(String risorsa) {
        this.risorsa = risorsa;
    }

    public String getProduzione() {

        return String.format(Locale.ITALY, "%.0f", produzione);
    }

    public void setProduzione(Double produzione) {
        this.produzione = produzione;
    }
}
