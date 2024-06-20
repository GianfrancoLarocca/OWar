package com.giancotsu.owar.event.difesa;

import com.giancotsu.owar.entity.player.PlayerArsenale;
import com.giancotsu.owar.entity.player.PlayerDifesa;
import com.giancotsu.owar.repository.player.SviluppoAstrattoRepository;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DifesaTryLvlUpSuccessListener implements ApplicationListener<DifesaTryLvlUpSuccessEvent> {

    private final RisorseService risorseService;
    private final PlayerService playerService;
    private final SviluppoAstrattoRepository sviluppoAstrattoRepository;

    public DifesaTryLvlUpSuccessListener(RisorseService risorseService, PlayerService playerService, SviluppoAstrattoRepository sviluppoAstrattoRepository) {
        this.risorseService = risorseService;
        this.playerService = playerService;
        this.sviluppoAstrattoRepository = sviluppoAstrattoRepository;
    }

    @Override
    public void onApplicationEvent(DifesaTryLvlUpSuccessEvent event) {
        PlayerDifesa pd = event.getPlayerDifesa();
        risorseService.initProdRes();

        Optional<String> nomeDifesa = sviluppoAstrattoRepository.getNameById(event.getPlayerDifesa().getDifesa().getId());
        if(nomeDifesa.isPresent()) {
            playerService.setNewActivity(event.getPlayer(), "Tentativo di alzare il livello",
                    String.format("Hai tentato di alzare il livello della difesa \"%s\" al livello %d, con successo!", nomeDifesa.get(), pd.getLivello()));
        } else {
            throw new RuntimeException("Difesa non trovata");
        }

        playerService.increasePlayerLvl(event.getPlayer(), event.getExp());
    }
}
