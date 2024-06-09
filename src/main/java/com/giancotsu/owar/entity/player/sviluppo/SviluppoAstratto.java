package com.giancotsu.owar.entity.player.sviluppo;

import com.giancotsu.owar.entity.risorse.RisorseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SviluppoAstratto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(name = "descrizione", length = 2048)
    private String descrizione;
    private String urlImmagine;

    private Double moltiplicatoreCosto = 1.2;

    @ElementCollection
    @CollectionTable(name = "map_costo_base_strutture", joinColumns = @JoinColumn(name = "strutture_id"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<RisorseEnum, Double> costoBase;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SviluppoAstratto that = (SviluppoAstratto) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
