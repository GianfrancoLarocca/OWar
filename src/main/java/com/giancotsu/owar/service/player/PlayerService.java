package com.giancotsu.owar.service.player;

import com.giancotsu.owar.entity.player.PlayerBasicInformationEntity;
import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerRisorse;
import com.giancotsu.owar.repository.player.BasicRepository;
import com.giancotsu.owar.repository.player.PlayerRepository;
import com.giancotsu.owar.repository.player.PlayerRisorseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BasicRepository basicRepository;
    private final PlayerRisorseRepository playerRisorseRepository;

    private static PlayerEntity loggedPlayer;

    public PlayerService(PlayerRepository playerRepository, BasicRepository basicRepository, PlayerRisorseRepository playerRisorseRepository) {
        this.playerRepository = playerRepository;
        this.basicRepository = basicRepository;
        this.playerRisorseRepository = playerRisorseRepository;
    }

    public static void setLoggedPlayer(PlayerEntity player) {
        loggedPlayer = player;
    }

    public static PlayerEntity getLoggedPlayer() {
        return loggedPlayer;
    }

    public ResponseEntity<PlayerEntity> getPlayer() {
        Optional<PlayerEntity> player = playerRepository.findById(loggedPlayer.getId());

        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformation() {
        Optional<PlayerBasicInformationEntity> basic = basicRepository.findById(loggedPlayer.getBasicInformation().getId());
        if (basic.isPresent()) {
            return new ResponseEntity<>(basic.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Deprecated
    public ResponseEntity<PlayerRisorse> getRisorsa() {
        Optional<PlayerRisorse> risorsa = playerRisorseRepository.findById(loggedPlayer.getPlayerRisorse().getId());
        if (risorsa.isPresent()) {
            return new ResponseEntity<>(risorsa.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}




























