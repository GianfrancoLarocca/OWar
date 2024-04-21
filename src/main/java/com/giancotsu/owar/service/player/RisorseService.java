package com.giancotsu.owar.service.player;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerRisorse;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.projection.SviluppoProduzioneRisorseProjection;
import com.giancotsu.owar.repository.player.PlayerRepository;
import com.giancotsu.owar.repository.player.PlayerSviluppoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RisorseService {

    private final int RESOURCE_UPDATE_INTERVAL = 1000 * 60; // Intervallo di aggiornamento delle risorse in millisecondi

    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final PlayerRepository playerRepository;

    private final Map<String, Double> resources = new HashMap<>();

    public RisorseService(PlayerSviluppoRepository playerSviluppoRepository, PlayerRepository playerRepository) {
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.playerRepository = playerRepository;
    }

    @Scheduled(fixedDelay = RESOURCE_UPDATE_INTERVAL)
    public void updateResources() {

        Set<Long> playerIds = playerRepository.getAllPlayerId();
        if(playerIds.size()>0) {
            for(Long playerId : playerIds) {
                Optional<PlayerEntity> playerOptional = playerRepository.findById(playerId);
                if (playerOptional.isPresent()) {
                    PlayerEntity player = playerOptional.get();
                    PlayerRisorse pr = player.getPlayerRisorse();

                    resources.put(RisorseEnum.MICROCHIP.name(), pr.getMicrochip().getQuantita());
                    resources.put(RisorseEnum.METALLO.name(), pr.getMetallo().getQuantita());
                    resources.put(RisorseEnum.ENERGIA.name(), pr.getEnergia().getQuantita());
                    resources.put(RisorseEnum.CIVILI.name(), pr.getCivili().getQuantita());
                    resources.put(RisorseEnum.BITCOIN.name(), pr.getBitcoin().getQuantita());
                    resources.put(RisorseEnum.ACQUA.name(), pr.getAcqua().getQuantita());

                    List<SviluppoProduzioneRisorseProjection> produzioneProj = playerSviluppoRepository.getProduzioneRisorse(player.getId());

                    produzioneProj.forEach(p -> {
                        resources.put(p.getRisorsa(), p.getProduzione() * p.getLivello() * (Math.pow(p.getMoltiplicatore(), p.getLivello())) + resources.get(p.getRisorsa()));
                    });
                    pr.getMicrochip().setQuantita(resources.get(RisorseEnum.MICROCHIP.name()));
                    pr.getMetallo().setQuantita(resources.get(RisorseEnum.METALLO.name()));
                    pr.getEnergia().setQuantita(resources.get(RisorseEnum.ENERGIA.name()));
                    pr.getCivili().setQuantita(resources.get(RisorseEnum.CIVILI.name()));
                    pr.getBitcoin().setQuantita(resources.get(RisorseEnum.BITCOIN.name()));
                    pr.getAcqua().setQuantita(resources.get(RisorseEnum.ACQUA.name()));
                    player.setPlayerRisorse(pr);
                    playerRepository.saveAndFlush(player);
                }
            }
        }
    }
}
