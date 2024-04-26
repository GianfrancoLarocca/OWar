package com.giancotsu.owar.service.player;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AlzaLivelloTry {

    private Random rand;
    private Double percentualeSuccesso;
    public AlzaLivelloTry() {
        rand = new Random();
        percentualeSuccesso = 0.0;
    }

    public Double getPercentualeSuccesso(Integer livello) {
        if (livello >= 0 && livello <= 10) {
            this.percentualeSuccesso = 100.0 - livello;
        } else if (livello >= 11 && livello <= 20) {
            this.percentualeSuccesso = 80.0 - ((livello - 10) * 2);
        } else if (livello >= 21 && livello <= 30) {
            this.percentualeSuccesso = 60.0 - ((livello - 20) * 3);
        } else if (livello >= 31 && livello <= 40) {
            this.percentualeSuccesso = 30.0 - ((livello - 30) * 2);
        } else if (livello >= 41 && livello < 50) {
            this.percentualeSuccesso = 10.0 - ((livello - 40) / 2.0);
        } else if (livello >= 50 && livello < 55) {
            this.percentualeSuccesso = 3.0;
        }  else if (livello >= 55 && livello < 60) {
            this.percentualeSuccesso = 1.0;
        } else if (livello >= 60) {
            this.percentualeSuccesso = 0.5;
        }

        return this.percentualeSuccesso;
    }

    public boolean alzaLivello(Integer livello) {

        Double percentuale = getPercentualeSuccesso(livello);

        double prova = this.rand.nextDouble();

        System.out.println("Percentuale successo: " + percentuale + "%" + " Prova: " + prova);

        if (prova < percentuale / 100) {
            System.err.println("Azione eseguita con successo! Livello aumentato.");
            return true;
        } else {
            System.err.println("Azione fallita. Il livello rimane invariato.");
            return false;
        }
    }
}
