package com.giancotsu.owar.entity.risorse;

import lombok.Data;
@Data
public class Acqua extends Risorsa {
    public Acqua() {
        super(RisorseEnum.ACQUA.name(), "LT", 0L, "../../assets/img/risorse/water.png");
    }
}
