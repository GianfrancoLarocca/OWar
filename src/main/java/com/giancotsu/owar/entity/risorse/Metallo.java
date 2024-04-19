package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Metallo extends Risorsa {



    public Metallo() {
        super(RisorseEnum.METALLO.name(), "Kg", 0L, "../../assets/img/risorse/metal.png");
    }
}
