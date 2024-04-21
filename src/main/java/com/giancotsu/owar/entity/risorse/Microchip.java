package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Microchip extends Risorsa {


    public Microchip() {
        super(RisorseEnum.MICROCHIP.name(), "Pezzi", 0.0, "../../assets/img/risorse/microchip.png");
    }
}
