package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Metallo extends Risorsa {



    public Metallo() {
        super(RisorseEnum.METALLO.name(), "Kg", 0.0, "../../assets/img/risorse/metal.png");
    }
}
