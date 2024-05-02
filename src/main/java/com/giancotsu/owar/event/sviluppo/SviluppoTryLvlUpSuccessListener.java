package com.giancotsu.owar.event.sviluppo;

import com.giancotsu.owar.entity.player.PlayerSviluppo;
import com.giancotsu.owar.repository.player.SviluppoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SviluppoTryLvlUpSuccessListener implements ApplicationListener<SviluppoTryLvlUpSuccessEvent> {

    private final RisorseService risorseService;
    private final PlayerService playerService;
    private final SviluppoRepository sviluppoRepository;

    public SviluppoTryLvlUpSuccessListener(RisorseService risorseService, PlayerService playerService, SviluppoRepository sviluppoRepository) {
        this.risorseService = risorseService;
        this.playerService = playerService;
        this.sviluppoRepository = sviluppoRepository;
    }

    @Override
    public void onApplicationEvent(SviluppoTryLvlUpSuccessEvent event) {
        PlayerSviluppo ps = event.getPs();
        risorseService.initProdRes();


        Optional<String> nomeStruttura = sviluppoRepository.getNameById(event.getPs().getSviluppo().getId());
        if(nomeStruttura.isPresent()) {
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello della struttura \"%s\" al livello %d, con successo!", nomeStruttura.get(), ps.getLivello()));
        } else {
            throw new RuntimeException("Struttura non trovata");
        }
    }
}