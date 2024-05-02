package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.ProduzioneRisorseDto;
import com.giancotsu.owar.dto.RisorsaDto;
import com.giancotsu.owar.dto.map.RisorseMapper;
import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerRisorse;
import com.giancotsu.owar.entity.risorse.*;
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

    private final int SECONDI = 1;
    private final int RESOURCE_UPDATE_INTERVAL = 1000 * SECONDI; // Intervallo di aggiornamento delle risorse in millisecondi
    private final int SAVE_RESOURCES_ON_DATABASE_INTERVAL = 1000 * 60;// * 60; // Intervallo di salvataggio delle risorse nel database in millisecondi

    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final JWTGenerator jwtGenerator;


    private final Map<Long, Map<String, Double>> playerResources = new HashMap<>();
    private Set<Long> playerIds = new HashSet<>();
    private final Map<Long, Map<String, Double>> quantityToUpdate = new HashMap<>();

    public RisorseService(PlayerSviluppoRepository playerSviluppoRepository, PlayerRepository playerRepository, UserRepository userRepository, JWTGenerator jwtGenerator) {
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;

        this.extractPlayersResourcesFromDb();
        this.initProdRes();
    }

    public void addNewPlayerResources(UserEntity user) {

        this.playerIds = playerRepository.getAllPlayerId();
        this.initProdRes();

        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(user.getPlayer().getId());
        if (optionalPlayer.isPresent()) {
            Map<String, Double> res = new HashMap<>();

            PlayerEntity player = optionalPlayer.get();
            PlayerRisorse pr = player.getPlayerRisorse();

            res.put(RisorseEnum.MICROCHIP.name(), pr.getMicrochip().getQuantita());
            res.put(RisorseEnum.METALLO.name(), pr.getMetallo().getQuantita());
            res.put(RisorseEnum.ENERGIA.name(), pr.getEnergia().getQuantita());
            res.put(RisorseEnum.CIVILI.name(), pr.getCivili().getQuantita());
            res.put(RisorseEnum.BITCOIN.name(), pr.getBitcoin().getQuantita());
            res.put(RisorseEnum.ACQUA.name(), pr.getAcqua().getQuantita());

            playerResources.put(player.getId(), res);
        }
    }

    private void extractPlayersResourcesFromDb() {
        Set<Long> playerIds = playerRepository.getAllPlayerId();
        if (!playerIds.isEmpty()) {
            for (Long playerId : playerIds) {

                PlayerEntity player = playerRepository.findById(playerId).get();
                PlayerRisorse pr = player.getPlayerRisorse();

                Map<String, Double> res = new HashMap<>();

                res.put(RisorseEnum.MICROCHIP.name(), pr.getMicrochip().getQuantita());
                res.put(RisorseEnum.METALLO.name(), pr.getMetallo().getQuantita());
                res.put(RisorseEnum.ENERGIA.name(), pr.getEnergia().getQuantita());
                res.put(RisorseEnum.CIVILI.name(), pr.getCivili().getQuantita());
                res.put(RisorseEnum.BITCOIN.name(), pr.getBitcoin().getQuantita());
                res.put(RisorseEnum.ACQUA.name(), pr.getAcqua().getQuantita());

                playerResources.put(playerId, res);

            }
        }
    }

    //metodo che aggiorna le risorse del player ogni tot di tempo
    @Scheduled(fixedDelay = RESOURCE_UPDATE_INTERVAL)
    private void updatePlayersResources() {

        if (!this.playerIds.isEmpty()) {
            for (Long playerId : playerIds) {
                playerResources.get(playerId).keySet().forEach(pr -> {
                    Double quantity = (quantityToUpdate.get(playerId).get(pr) / 60.0) * SECONDI;
                    playerResources.get(playerId).put(pr, playerResources.get(playerId).get(pr) + quantity);
                });
            }
        }
    }

    public void initProdRes() {
        this.playerIds = playerRepository.getAllPlayerId();
        this.playerIds.forEach(this::getProductionResources);
    }

    //metodo che restituisce la produzione per ogni risorsa al minuto del player
    private Map<String, Double> getProductionResources(Long playerId) {

        List<SviluppoProduzioneRisorseProjection> produzioneProj = playerSviluppoRepository.getProduzioneRisorse(playerId);

        Map<String, Double> prod = new HashMap<>();

        produzioneProj.forEach(p -> {
            Double valoreAttuale;
            if (prod.get(p.getRisorsa()) == null) {
                valoreAttuale = 0.0;
            } else {
                valoreAttuale = prod.get(p.getRisorsa());
            }
            prod.put(p.getRisorsa(), (p.getProduzione() * p.getLivello() * (Math.pow(p.getMoltiplicatore(), p.getLivello()))) + valoreAttuale);
            this.quantityToUpdate.put(playerId, prod);
        });

        return prod;
    }

    //metodo che restituisce la produzione delle risorse sottoforma di dto
    public ResponseEntity<List<ProduzioneRisorseDto>> getProductionResourcesDto(String bearerToken) {

        List<ProduzioneRisorseDto> prodDto = new ArrayList<>();

        Optional<PlayerEntity> playerOptional = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (playerOptional.isPresent()) {

            Map<String, Double> prod = this.getProductionResources(playerOptional.get().getId());

            prod.keySet().forEach(p -> {
                ProduzioneRisorseDto dto = new ProduzioneRisorseDto(p, prod.get(p));
                prodDto.add(dto);
            });
        } else {
            throw new UsernameNotFoundException("User not found");
        }

        return new ResponseEntity<>(prodDto, HttpStatus.OK);
    }

    //metodo che restituisce le risorse del player che ha fatto la richiesta
    public ResponseEntity<List<RisorsaDto>> getPlayerResourcesDto(String bearerToken) {

        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);

        Map<String, Double> playerRes = playerResources.get(playerId);

        Microchip mc = new Microchip(playerRes.get(RisorseEnum.MICROCHIP.name()));
        Metallo met = new Metallo(playerRes.get(RisorseEnum.METALLO.name()));
        Energia ene = new Energia(playerRes.get(RisorseEnum.ENERGIA.name()));
        Civili civ = new Civili(playerRes.get(RisorseEnum.CIVILI.name()));
        Bitcoin bit = new Bitcoin(playerRes.get(RisorseEnum.BITCOIN.name()));
        Acqua a = new Acqua(playerRes.get(RisorseEnum.ACQUA.name()));

        PlayerRisorse pr = new PlayerRisorse(mc, met, ene, civ, bit, a);

        List<RisorsaDto> risorseDto = RisorseMapper.mapToDto(pr);
        return new ResponseEntity<>(risorseDto, HttpStatus.OK);
    }

    public Map<String, Double> getPlayerResources(Long playerId) {
        return playerResources.get(playerId);
    }

    public boolean payment(Map<String, Double> costi, Long playerId) {

        Map<String, Double> playerRes = playerResources.get(playerId);
        costi.keySet().forEach(risorsa -> {
            playerRes.put(risorsa, playerRes.get(risorsa) - costi.get(risorsa));
        });
        playerResources.put(playerId, playerRes);
        return true;
    }

    @Scheduled(fixedDelay = SAVE_RESOURCES_ON_DATABASE_INTERVAL)
    private void savePlayerResourcesOnDatabase() {

        Set<Long> playerIds = playerRepository.getAllPlayerId();
        if (!playerIds.isEmpty()) {
            for (Long playerId : playerIds) {

                PlayerEntity player = playerRepository.findById(playerId).get();
                PlayerRisorse pr = player.getPlayerRisorse();

                Map<String, Double> playerRes = playerResources.get(playerId);

                pr.getMicrochip().setQuantita(playerRes.get(RisorseEnum.MICROCHIP.name()));
                pr.getMetallo().setQuantita(playerRes.get(RisorseEnum.METALLO.name()));
                pr.getEnergia().setQuantita(playerRes.get(RisorseEnum.ENERGIA.name()));
                pr.getCivili().setQuantita(playerRes.get(RisorseEnum.CIVILI.name()));
                pr.getBitcoin().setQuantita(playerRes.get(RisorseEnum.BITCOIN.name()));
                pr.getAcqua().setQuantita(playerRes.get(RisorseEnum.ACQUA.name()));

                playerRepository.save(player);
            }
        }
    }

    private String getJWTFromHeaderRequest(String authorizationToken) {
        if (StringUtils.hasText(authorizationToken) && authorizationToken.startsWith("Bearer ")) {
            return authorizationToken.substring(7);
        } else {
            return null;
        }
    }

    private Long getPlayerIdByAuthorizationToken(String authorizationToken) {
        String jwt = this.getJWTFromHeaderRequest(authorizationToken);
        String username = jwtGenerator.extractUsername(jwt);
        return userRepository.getPlayerIdByUsername(username);
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
