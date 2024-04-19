package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Civili extends Risorsa {



    public Civili() {
        super(RisorseEnum.CIVILI.name(), "Persone", 0L, "../../assets/img/risorse/people.png");
    }
}
