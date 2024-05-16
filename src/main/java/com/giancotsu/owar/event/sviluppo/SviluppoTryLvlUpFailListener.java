package com.giancotsu.owar.event.sviluppo;

import com.giancotsu.owar.repository.player.SviluppoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SviluppoTryLvlUpFailListener implements ApplicationListener<SviluppoTryLvlUpFailEvent> {

    private final PlayerService playerService;
    private final SviluppoRepository sviluppoRepository;

    public SviluppoTryLvlUpFailListener(PlayerService playerService, SviluppoRepository sviluppoRepository) {
        this.playerService = playerService;
        this.sviluppoRepository = sviluppoRepository;
    }

    @Override
    public void onApplicationEvent(SviluppoTryLvlUpFailEvent event) {
        Optional<String> nomeStruttura = sviluppoRepository.getNameById(event.getPs().getSviluppo().getId());
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
