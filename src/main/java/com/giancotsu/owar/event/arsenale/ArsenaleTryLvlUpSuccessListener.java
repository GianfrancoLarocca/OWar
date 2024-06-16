package com.giancotsu.owar.event.arsenale;

import com.giancotsu.owar.entity.player.PlayerArsenale;
import com.giancotsu.owar.repository.player.SviluppoAstrattoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArsenaleTryLvlUpSuccessListener implements ApplicationListener<ArsenaleTryLvlUpSuccessEvent> {

    private final RisorseService risorseService;
    private final PlayerService playerService;
    private final SviluppoAstrattoRepository sviluppoAstrattoRepository;

    public ArsenaleTryLvlUpSuccessListener(RisorseService risorseService, PlayerService playerService, SviluppoAstrattoRepository sviluppoAstrattoRepository) {
        this.risorseService = risorseService;
        this.playerService = playerService;
        this.sviluppoAstrattoRepository = sviluppoAstrattoRepository;
    }

    @Override
    public void onApplicationEvent(ArsenaleTryLvlUpSuccessEvent event) {
        PlayerArsenale pa = event.getPlayerArsenale();
        risorseService.initProdRes();

        Optional<String> nomeArsenale = sviluppoAstrattoRepository.getNameById(event.getPlayerArsenale().getArsenale().getId());
        if(nomeArsenale.isPresent()) {
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello dell'arma o veicolo \"%s\" al livello %d, con successo!", nomeArsenale.get(), pa.getLivello()));
        } else {
            throw new RuntimeException("Arsenale non trovato");
        }

        playerService.increasePlayerLvl(event.getPlayer(), event.getExp());
    }
}
