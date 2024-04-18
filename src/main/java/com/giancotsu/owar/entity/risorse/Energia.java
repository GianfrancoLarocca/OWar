package com.giancotsu.owar.entity.risorse;

import lombok.Data;

@Data
public class Energia extends Risorsa {

    public Energia() {
        super(RisorseEnum.ENERGIA.name(), "KJ", 0L, "../../assets/img/risorse/energy.png");
    }
}
