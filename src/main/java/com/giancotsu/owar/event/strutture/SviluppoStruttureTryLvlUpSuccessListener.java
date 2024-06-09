package com.giancotsu.owar.event.strutture;

import com.giancotsu.owar.entity.player.PlayerStrutture;
import com.giancotsu.owar.repository.player.SviluppoAstrattoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SviluppoStruttureTryLvlUpSuccessListener implements ApplicationListener<SviluppoStruttureTryLvlUpSuccessEvent>  {

    private final RisorseService risorseService;
    private final PlayerService playerService;
    private final SviluppoAstrattoRepository sviluppoAstrattoRepository;

    public SviluppoStruttureTryLvlUpSuccessListener(RisorseService risorseService, PlayerService playerService, SviluppoAstrattoRepository sviluppoAstrattoRepository) {
        this.risorseService = risorseService;
        this.playerService = playerService;
        this.sviluppoAstrattoRepository = sviluppoAstrattoRepository;
    }

    @Override
    public void onApplicationEvent(SviluppoStruttureTryLvlUpSuccessEvent event) {
        PlayerStrutture ps = event.getPs();
        risorseService.initProdRes();

        Optional<String> nomeStruttura = sviluppoAstrattoRepository.getNameById(event.getPs().getStrutture().getId());
        if(nomeStruttura.isPresent()) {
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello della struttura \"%s\" al livello %d, con successo!", nomeStruttura.get(), ps.getLivello()));
        } else {
            throw new RuntimeException("Struttura non trovata");
        }

        playerService.increasePlayerLvl(event.getPlayer(), event.getExp());
    }
}
