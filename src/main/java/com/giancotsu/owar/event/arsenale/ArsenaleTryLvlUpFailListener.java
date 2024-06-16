package com.giancotsu.owar.event.arsenale;

import com.giancotsu.owar.repository.player.SviluppoAstrattoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArsenaleTryLvlUpFailListener implements ApplicationListener<ArsenaleTryLvlUpFailEvent> {

    private final PlayerService playerService;
    private final SviluppoAstrattoRepository sviluppoAstrattoRepository;

    public ArsenaleTryLvlUpFailListener(PlayerService playerService, SviluppoAstrattoRepository sviluppoAstrattoRepository) {
        this.playerService = playerService;
        this.sviluppoAstrattoRepository = sviluppoAstrattoRepository;
    }

    @Override
    public void onApplicationEvent(ArsenaleTryLvlUpFailEvent event) {
        Optional<String> nomeArsenale = sviluppoAstrattoRepository.getNameById(event.getPlayerArsenale().getArsenale().getId());
        if(nomeArsenale.isPresent()) {
            int livello = event.getPlayerArsenale().getLivello();
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello dell'arma o veicolo \"%s\" al livello %d, ma hai fallito! Il livello rimane al %d.",
                            nomeArsenale.get(), livello + 1, livello));
        } else {
            throw new RuntimeException("Arsenale non trovato");
        }
    }
}
