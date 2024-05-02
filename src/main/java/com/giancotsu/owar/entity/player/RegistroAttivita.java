package com.giancotsu.owar.entity.player;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class RegistroAttivita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "registroAttivita", cascade = CascadeType.ALL)
    List<Attivita> attivita = new ArrayList<>();
}
