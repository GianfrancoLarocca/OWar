package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Civili extends Risorsa {



    public Civili() {
        super(RisorseEnum.CIVILI.name(), "Persone", 80000.0, "../../assets/img/risorse/people.png");
    }

    public Civili(Double quantity) {
        super(RisorseEnum.CIVILI.name(), "Persone", quantity, "../../assets/img/risorse/people.png");
    }
}
