package com.giancotsu.owar.event.strutture;

import com.giancotsu.owar.repository.player.SviluppoAstrattoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SviluppoStruttureTryLvlUpFailListener implements ApplicationListener<SviluppoStruttureTryLvlUpFailEvent> {

    private final PlayerService playerService;
    private final SviluppoAstrattoRepository sviluppoAstrattoRepository;

    public SviluppoStruttureTryLvlUpFailListener(PlayerService playerService, SviluppoAstrattoRepository sviluppoAstrattoRepository) {
        this.playerService = playerService;
        this.sviluppoAstrattoRepository = sviluppoAstrattoRepository;
    }

    @Override
    public void onApplicationEvent(SviluppoStruttureTryLvlUpFailEvent event) {
        Optional<String> nomeStruttura = sviluppoAstrattoRepository.getNameById(event.getPs().getStrutture().getId());
        if(nomeStruttura.isPresent()) {
            int livello = event.getPs().getLivello();
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello della struttura \"%s\" al livello %d, ma hai fallito! Il livello della struttura è ancora %d.",
                            nomeStruttura.get(), livello + 1, livello));
        } else {
            throw new RuntimeException("Struttura non trovata");
        }
    }
}
