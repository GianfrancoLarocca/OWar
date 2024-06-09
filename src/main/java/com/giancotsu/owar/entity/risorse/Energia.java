package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;
@Entity
@Data
public class Energia extends Risorsa {



    public Energia() {
        super(RisorseEnum.ENERGIA.name(), "KJ", 100000.0, "../../assets/img/risorse/energy.png");
    }

    public Energia(Double quantity) {
        super(RisorseEnum.ENERGIA.name(), "KJ", quantity, "../../assets/img/risorse/energy.png");
    }
}
