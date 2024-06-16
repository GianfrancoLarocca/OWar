package com.giancotsu.owar.event.tecnologia;

import com.giancotsu.owar.entity.player.PlayerTecnologia;
import com.giancotsu.owar.repository.player.SviluppoAstrattoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;

import java.util.Optional;

public class TecnologiaTryLvlUpFailListener implements ApplicationListener<TecnologiaTryLvlUpFailEvent> {

    private final PlayerService playerService;
    private final SviluppoAstrattoRepository sviluppoAstrattoRepository;

    public TecnologiaTryLvlUpFailListener(PlayerService playerService, SviluppoAstrattoRepository sviluppoAstrattoRepository) {
        this.playerService = playerService;
        this.sviluppoAstrattoRepository = sviluppoAstrattoRepository;
    }

    @Override
    public void onApplicationEvent(TecnologiaTryLvlUpFailEvent event) {
        Optional<String> nomeTech = sviluppoAstrattoRepository.getNameById(event.getPs().getTecnologia().getId());
        if(nomeTech.isPresent()) {
            int livello = event.getPs().getLivello();
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello della tecnologia \"%s\" al livello %d, ma hai fallito! Il livello della tecnologia rimane %d.",
                            nomeTech.get(), livello + 1, livello));
        } else {
            throw new RuntimeException("Tecnologia non trovata");
        }
    }
}
