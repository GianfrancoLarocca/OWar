package com.giancotsu.owar.event.difesa;

import com.giancotsu.owar.repository.player.SviluppoAstrattoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DifesaTryLvlUpFailListener implements ApplicationListener<DifesaTryLvlUpFailEvent> {

    private final PlayerService playerService;
    private final SviluppoAstrattoRepository sviluppoAstrattoRepository;

    public DifesaTryLvlUpFailListener(PlayerService playerService, SviluppoAstrattoRepository sviluppoAstrattoRepository) {
        this.playerService = playerService;
        this.sviluppoAstrattoRepository = sviluppoAstrattoRepository;
    }


    @Override
    public void onApplicationEvent(DifesaTryLvlUpFailEvent event) {
        Optional<String> nomeDifesa = sviluppoAstrattoRepository.getNameById(event.getPlayerDifesa().getDifesa().getId());
        if(nomeDifesa.isPresent()) {
            int livello = event.getPlayerDifesa().getLivello();
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello della difesa \"%s\" al livello %d, ma hai fallito! Il livello rimane al %d.",
                            nomeDifesa.get(), livello + 1, livello));
        } else {
            throw new RuntimeException("Difesa non trovata");
        }
    }
}
