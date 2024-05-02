package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;
@Entity
@Data
public class Acqua extends Risorsa {


    public Acqua() {
        super(RisorseEnum.ACQUA.name(), "LT", 80000.0, "../../assets/img/risorse/water.png");
    }

    public Acqua(Double quantity) {
        super(RisorseEnum.ACQUA.name(), "LT", quantity, "../../assets/img/risorse/water.png");
    }
}
