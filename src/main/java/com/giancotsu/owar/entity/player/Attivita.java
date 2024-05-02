package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@ToString
public class Attivita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime data;
    private String azione;
    private String descrizione;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "registro_attivita_id")
    @JsonIgnore
    private RegistroAttivita registroAttivita;

    public Attivita(String azione, String descrizione) {
        this.azione = azione;
        this.descrizione = descrizione;

        this.data = ZonedDateTime.now(ZoneId.of("Europe/Rome"));
    }

    public String getData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        return data.format(formatter);
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getAzione() {
        return azione;
    }

    public void setAzione(String azione) {
        this.azione = azione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public RegistroAttivita getRegistroAttivita() {
        return registroAttivita;
    }

    public void setRegistroAttivita(RegistroAttivita registroAttivita) {
        this.registroAttivita = registroAttivita;
    }
}
