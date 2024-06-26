package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Microchip extends Risorsa {


    public Microchip() {
        super(RisorseEnum.MICROCHIP.name(), "Pezzi", 100000.0, "../../assets/img/risorse/microchip.png");
    }

    public Microchip( Double quantity) {
        super(RisorseEnum.MICROCHIP.name(), "Pezzi", quantity, "../../assets/img/risorse/microchip.png");
    }
}
