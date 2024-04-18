package com.giancotsu.owar.controller;

import com.giancotsu.owar.dto.SviluppoCompletoDto;
import com.giancotsu.owar.entity.player.PlayerBasicInformationEntity;
import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.entity.player.PlayerRisorse;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.PlayerSviluppoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerSviluppoService playerSviluppoService;

    public PlayerController(PlayerService playerService, PlayerSviluppoService playerSviluppoService) {
        this.playerService = playerService;
        this.playerSviluppoService = playerSviluppoService;
    }

    @GetMapping()
    public ResponseEntity<PlayerEntity> getPlayer() {
        return this.playerService.getPlayer();
    }

    @GetMapping(value = "basic")
    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformation() {
        return this.playerService.getPlayerBasicInformation();
    }

    @GetMapping(value = "risorse")
    public ResponseEntity<PlayerRisorse> getPlayerRisorse() {
        return this.playerService.getRisorsa();
    }

    @GetMapping(value = "strutture/{sviluppoId}")
    public ResponseEntity<SviluppoCompletoDto> getSviluppoDett(@PathVariable("sviluppoId") Long sviluppoId) {
        return this.playerSviluppoService.getSviluppoDett(sviluppoId);
    }
}
