package com.giancotsu.owar.event.tecnologia;

import com.giancotsu.owar.entity.player.PlayerStrutture;
import com.giancotsu.owar.entity.player.PlayerTecnologia;
import com.giancotsu.owar.event.strutture.SviluppoStruttureTryLvlUpSuccessEvent;
import com.giancotsu.owar.repository.player.SviluppoAstrattoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TecnologiaTryLvlUpSuccessListener implements ApplicationListener<TecnologiaTryLvlUpSuccessEvent> {

    private final RisorseService risorseService;
    private final PlayerService playerService;
    private final SviluppoAstrattoRepository sviluppoAstrattoRepository;

    public TecnologiaTryLvlUpSuccessListener(RisorseService risorseService, PlayerService playerService, SviluppoAstrattoRepository sviluppoAstrattoRepository) {
        this.risorseService = risorseService;
        this.playerService = playerService;
        this.sviluppoAstrattoRepository = sviluppoAstrattoRepository;
    }

    @Override
    public void onApplicationEvent(TecnologiaTryLvlUpSuccessEvent event) {
        PlayerTecnologia pt = event.getPs();
        risorseService.initProdRes();

        Optional<String> nomeTech = sviluppoAstrattoRepository.getNameById(event.getPs().getTecnologia().getId());
        if(nomeTech.isPresent()) {
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello della tecnologia \"%s\" al livello %d, con successo!", nomeTech.get(), pt.getLivello()));
        } else {
            throw new RuntimeException("Tecnologia non trovata");
        }

        playerService.increasePlayerLvl(event.getPlayer(), event.getExp());
    }
}
