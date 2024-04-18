package com.giancotsu.owar.entity.risorse;

import lombok.Data;


@Data
public class Bitcoin extends Risorsa {

    public Bitcoin() {
        super(RisorseEnum.BITCOIN.name(), "BTC", 0L, "../../assets/img/risorse/bank.png");
    }
}
