package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Bitcoin extends Risorsa {



    public Bitcoin() {
        super(RisorseEnum.BITCOIN.name(), "BTC", 0L, "../../assets/img/risorse/bank.png");
    }
}
