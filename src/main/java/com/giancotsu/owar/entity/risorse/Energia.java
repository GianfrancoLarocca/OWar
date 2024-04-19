package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;
@Entity
@Data
public class Energia extends Risorsa {



    public Energia() {
        super(RisorseEnum.ENERGIA.name(), "KJ", 0L, "../../assets/img/risorse/energy.png");
    }
}
