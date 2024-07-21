package com.giancotsu.owar.dto;

import lombok.Data;

@Data
public class UnitaComprateEsercito {

    private long soldatoId;
    private long numeroSoldatiDaComprare;
    private int secondiAddestramento;
    private long tempoRimanenteMillisecondi;
    private long tempoRimanenteTotaleMillisecondi;

    public UnitaComprateEsercito(long soldatoId, long numeroSoldatiDaComprare, int secondiAddestramento) {
        this.soldatoId = soldatoId;
        this.numeroSoldatiDaComprare = numeroSoldatiDaComprare;
        this.secondiAddestramento = secondiAddestramento;
    }
}
