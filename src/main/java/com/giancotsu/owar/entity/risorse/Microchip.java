package com.giancotsu.owar.entity.risorse;

import lombok.Data;

@Data
public class Microchip extends Risorsa {
    public Microchip() {
        super(RisorseEnum.MICROCHIP.name(), "Pezzi", 0L, "../../assets/img/risorse/microchip.png");
    }
}
