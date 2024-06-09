package com.giancotsu.owar.entity.risorse;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Bitcoin extends Risorsa {



    public Bitcoin() {
        super(RisorseEnum.BITCOIN.name(), "BTC", 100000.0, "../../assets/img/risorse/bank.png");
    }

    public Bitcoin(Double quantity) {
        super(RisorseEnum.BITCOIN.name(), "BTC", quantity, "../../assets/img/risorse/bank.png");
    }
}
