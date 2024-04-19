package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;
@Entity
@Data
public class Acqua extends Risorsa {


    public Acqua() {
        super(RisorseEnum.ACQUA.name(), "LT", 0L, "../../assets/img/risorse/water.png");
    }
}
