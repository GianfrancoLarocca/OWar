package com.giancotsu.owar.entity.risorse;

import lombok.Data;

@Data
public class Metallo extends Risorsa {

    public Metallo() {
        super(RisorseEnum.METALLO.name(), "Kg", 0L, "../../assets/img/risorse/metal.png");
    }
}
