package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.ProduzioneRisorseDto;
import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerRisorse;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.SviluppoProduzioneRisorseProjection;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.PlayerRepository;
import com.giancotsu.owar.repository.player.PlayerSviluppoRepository;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class RisorseService {

    private final int RESOURCE_UPDATE_INTERVAL = 1000 * 10; // Intervallo di aggiornamento delle risorse in millisecondi

    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final JWTGenerator jwtGenerator;

    private final Map<String, Double> resources = new HashMap<>();

    public RisorseService(PlayerSviluppoRepository playerSviluppoRepository, PlayerRepository playerRepository, UserRepository userRepository, JWTGenerator jwtGenerator) {
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    //metodo che aggiorna le risorse ogni 10 secondi di tutti i player
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
                        resources.put(p.getRisorsa(), (p.getProduzione() * p.getLivello() * (Math.pow(p.getMoltiplicatore(), p.getLivello()))) / 6 + resources.get(p.getRisorsa()));
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

    //metodo che restituisce la produzione al minuto di ogni risorsa del player
    public ResponseEntity<List<ProduzioneRisorseDto>> getProductionResources(String bearerToken) {

        Map<String, Double> prod = new HashMap<>();
        List<ProduzioneRisorseDto> prodDto = new ArrayList<>();

        Optional<PlayerEntity> playerOptional = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (playerOptional.isPresent()) {
            PlayerEntity player = playerOptional.get();

            List<SviluppoProduzioneRisorseProjection> produzioneProj = playerSviluppoRepository.getProduzioneRisorse(player.getId());

            produzioneProj.forEach(p -> {
                Double valoreAttuale = 0.0;
                if(prod.get(p.getRisorsa()) == null) {
                    valoreAttuale = 0.0;
                } else {
                    valoreAttuale = prod.get(p.getRisorsa());
                }
                prod.put(p.getRisorsa(), (p.getProduzione() * p.getLivello() * (Math.pow(p.getMoltiplicatore(), p.getLivello()))) + valoreAttuale);
            });
            prod.keySet().forEach(p -> {
                ProduzioneRisorseDto dto = new ProduzioneRisorseDto(p, prod.get(p));
                prodDto.add(dto);
            });
        } else {
            throw new UsernameNotFoundException("User not found");
        }

        return new ResponseEntity<>(prodDto, HttpStatus.OK);
    }

    private String getJWTFromHeaderRequest(String authorizationToken) {
        if (StringUtils.hasText(authorizationToken) && authorizationToken.startsWith("Bearer ")) {
            return authorizationToken.substring(7);
        } else {
            return null;
        }
    }

    private UserEntity getUserFromAuthorizationToken(String authorizationToken) {
        String jwt = this.getJWTFromHeaderRequest(authorizationToken);
        String username = jwtGenerator.extractUsername(jwt);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
