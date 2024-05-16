package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.RisorsaDto;
import com.giancotsu.owar.dto.map.RisorseMapper;
import com.giancotsu.owar.entity.player.*;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.event.sviluppo.SviluppoTryLvlUpFailEvent;
import com.giancotsu.owar.event.sviluppo.SviluppoTryLvlUpSuccessEvent;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.*;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BasicRepository basicRepository;
    private final PlayerRisorseRepository playerRisorseRepository;
    private final UserRepository userRepository;
    private final AttivitaRepository attivitaRepository;
    private final CostiService costiService;
    private final JWTGenerator jwtGenerator;
    private final AlzaLivelloTry alzaLivelloTry;
    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PlayerService(PlayerRepository playerRepository, BasicRepository basicRepository, PlayerRisorseRepository playerRisorseRepository, UserRepository userRepository, AttivitaRepository attivitaRepository, CostiService costiService, JWTGenerator jwtGenerator, AlzaLivelloTry alzaLivelloTry, PlayerSviluppoRepository playerSviluppoRepository, ApplicationEventPublisher eventPublisher) {
        this.playerRepository = playerRepository;
        this.basicRepository = basicRepository;
        this.playerRisorseRepository = playerRisorseRepository;
        this.userRepository = userRepository;
        this.attivitaRepository = attivitaRepository;
        this.costiService = costiService;
        this.jwtGenerator = jwtGenerator;
        this.alzaLivelloTry = alzaLivelloTry;
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.eventPublisher = eventPublisher;
    }

    public ResponseEntity<PlayerEntity> getPlayer(String bearerToken) {

        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());

        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformation(String bearerToken) {

        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (player.isPresent()) {
            return new ResponseEntity<>(player.get().getBasicInformation(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformationByNickname(String nickname) {

        Optional<PlayerBasicInformationEntity> playerBasic = basicRepository.findByNickname(nickname);
        if (playerBasic.isPresent()) {
            return new ResponseEntity<>(playerBasic.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Exp(n)=Base×(Livello×Fattore)
    //Exp(n) = Base * Math.pow(Fattore, livello - 1.0);
    public PlayerBasicInformationEntity increasePlayerLvl(PlayerEntity player, Double exp) {


        PlayerBasicInformationEntity basicInformation = player.getBasicInformation();
        PlayerEntity updatedPlayer;
        int livello = basicInformation.getLivello();
        Double experience = basicInformation.getExp();
        Double expBase = basicInformation.getExpBase();
        Double expFactor = basicInformation.getExpFactor();
        Double expNextLevel = basicInformation.getExpNextLevel();
        Double expTot = basicInformation.getExpTot();

        if (experience + exp >= expNextLevel) {
            while (experience + exp >= expNextLevel) {
                livello++;
                double expStartLvl = expBase * Math.pow(expFactor, livello - 1.0);
                basicInformation.setLivello(livello);
                expNextLevel = expBase * Math.pow(expFactor, livello);
                basicInformation.setExpStartLvl(expStartLvl);
                basicInformation.setExpNextLevel(expNextLevel);
                basicInformation.setExp((experience + exp) - expStartLvl);
            }
        } else {
            basicInformation.setExp(experience + exp);
        }

        basicInformation.setExpTot(expTot + exp);
        player.setBasicInformation(basicInformation);
        updatedPlayer = playerRepository.save(player);

        return updatedPlayer.getBasicInformation();

    }

    public ResponseEntity<List<Attivita>> getRegistroAttivita(String bearerToken) {

        List<Attivita> attivita = attivitaRepository.getAttivitaByPlayerId(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());

        return new ResponseEntity<>(attivita, HttpStatus.OK);

    }

    public ResponseEntity<Page<Attivita>> getRegistroAttivitaPageable(String bearerToken, int pageNumber, int pageSize) {

        long playerId = getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Attivita> attivita = attivitaRepository.getAttivitaByPlayerIdPageable(playerId, pageable);

        return new ResponseEntity<>(attivita, HttpStatus.OK);

    }

    public void setNewActivity(PlayerEntity player, String activity, String description) {
        Attivita attivita = new Attivita(activity, description);
        attivita.setRegistroAttivita(player.getRegistroAttivita());

        player.getRegistroAttivita().getAttivita().add(attivita);
        playerRepository.save(player);
    }

    @Deprecated
    public ResponseEntity<List<RisorsaDto>> getRisorsa(String bearerToken) {
        Optional<PlayerRisorse> risorsa = playerRisorseRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (risorsa.isPresent()) {
            List<RisorsaDto> risorseDto = RisorseMapper.mapToDto(risorsa.get());
            return new ResponseEntity<>(risorseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> getChance(Long sviluppoId, String authorizationToken) {
        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(getUserFromAuthorizationToken(authorizationToken).getPlayer().getId());
        if (optionalPlayer.isPresent()) {
            PlayerEntity player = optionalPlayer.get();
            Optional<PlayerSviluppo> ops = playerSviluppoRepository.findByPlayerIdAndSviluppoId(player.getId(), sviluppoId);
            if (ops.isPresent()) {
                PlayerSviluppo ps = ops.get();
                if (player.getId() == ps.getPlayer().getId()) {
                    int livello = ps.getLivello();
                    String chance = String.format("%.0f", alzaLivelloTry.getPercentualeSuccesso(livello));
                    return new ResponseEntity<>(chance, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> provaAlzaLivello(Long sviluppoId, String authorizationToken) {

        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(getUserFromAuthorizationToken(authorizationToken).getPlayer().getId());
        if (optionalPlayer.isPresent()) {
            PlayerEntity player = optionalPlayer.get();
            Optional<PlayerSviluppo> ops = playerSviluppoRepository.findByPlayerIdAndSviluppoId(player.getId(), sviluppoId);
            if (ops.isPresent()) {
                PlayerSviluppo ps = ops.get();
                if (player.getId() == ps.getPlayer().getId()) {
                    if (!costiService.canPay(sviluppoId, authorizationToken)) {
                        return new ResponseEntity<>("No money", HttpStatus.BAD_REQUEST);
                    }
                    costiService.pay(sviluppoId, authorizationToken);
                    int livello = ps.getLivello();
                    boolean risultato = alzaLivelloTry.alzaLivello(livello);
                    //evento: tentativo di alzare il livello
                    if (risultato) {
                        ps.setLivello(livello + 1);
                        playerSviluppoRepository.save(ps);
                        Double exp = costiService.convertCostToExp(sviluppoId, authorizationToken);
                        eventPublisher.publishEvent(new SviluppoTryLvlUpSuccessEvent(this, player, ps, exp));
                        return new ResponseEntity<>("success", HttpStatus.OK);
                    } else {
                        eventPublisher.publishEvent(new SviluppoTryLvlUpFailEvent(this, player, ps));
                        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("building error", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("player error", HttpStatus.BAD_REQUEST);
        }

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




























