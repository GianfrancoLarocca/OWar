package com.giancotsu.owar.service.player;

import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerRisorse;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.SviluppoCrescitaRisorseProjection;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.PlayerRepository;
import com.giancotsu.owar.repository.player.PlayerRisorseRepository;
import com.giancotsu.owar.repository.player.PlayerSviluppoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RisorseService {

    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final PlayerRisorseRepository playerRisorseRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    private Map<String, Long> resources = new HashMap<>();

    public RisorseService(PlayerSviluppoRepository playerSviluppoRepository, PlayerRisorseRepository playerRisorseRepository, PlayerRepository playerRepository, UserRepository userRepository) {
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.playerRisorseRepository = playerRisorseRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
    }

    public void updateResources() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByUsername(auth.getName());
        if(user.isPresent()) {
            Long playerId = user.get().getPlayer().getId();
            Optional<PlayerEntity> playerOptional = playerRepository.findById(playerId);
            if(playerOptional.isPresent()) {
                PlayerEntity player = playerOptional.get();
                PlayerRisorse pr = player.getPlayerRisorse();

                resources.put(RisorseEnum.MICROCHIP.name(), pr.getMicrochip().getQuantita());
                resources.put(RisorseEnum.METALLO.name(), pr.getMetallo().getQuantita());
                resources.put(RisorseEnum.ENERGIA.name(), pr.getEnergia().getQuantita());
                resources.put(RisorseEnum.CIVILI.name(), pr.getCivili().getQuantita());
                resources.put(RisorseEnum.BITCOIN.name(), pr.getBitcoin().getQuantita());
                resources.put(RisorseEnum.ACQUA.name(), pr.getAcqua().getQuantita());
                System.err.println("inizio " + resources);

                List<SviluppoCrescitaRisorseProjection> crescitaProj = playerSviluppoRepository.getCrescitaRisorse(player.getId());

                crescitaProj.forEach(p -> {
                    resources.put(p.getRisorsa(), Math.round(p.getCrescita() * (Math.pow(p.getMoltiplicatore(), p.getLivello())) + resources.get(p.getRisorsa())));
                });
                System.err.println("update " + resources);
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


    public Map<String, Long> getResources() {
        return resources;
    }
}
