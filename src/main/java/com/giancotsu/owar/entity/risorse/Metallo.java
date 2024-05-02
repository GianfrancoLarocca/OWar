package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Metallo extends Risorsa {



    public Metallo() {
        super(RisorseEnum.METALLO.name(), "Kg", 80000.0, "../../assets/img/risorse/metal.png");
    }

    public Metallo(Double quantity) {
        super(RisorseEnum.METALLO.name(), "Kg", quantity, "../../assets/img/risorse/metal.png");
    }
}
