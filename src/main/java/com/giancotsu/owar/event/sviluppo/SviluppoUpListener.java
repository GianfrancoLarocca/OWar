package com.giancotsu.owar.event.sviluppo;

import com.giancotsu.owar.entity.player.PlayerSviluppo;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SviluppoUpListener implements ApplicationListener<SviluppoUpEvent> {

    private final RisorseService risorseService;

    public SviluppoUpListener(RisorseService risorseService) {
        this.risorseService = risorseService;
    }

    @Override
    public void onApplicationEvent(SviluppoUpEvent event) {
        PlayerSviluppo ps = event.getPs();
        risorseService.initProdRes();
    }
}
