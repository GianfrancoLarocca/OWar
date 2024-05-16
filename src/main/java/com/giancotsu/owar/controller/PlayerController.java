package com.giancotsu.owar.controller;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.ProduzioneRisorseDto;
import com.giancotsu.owar.dto.RisorsaDto;
import com.giancotsu.owar.dto.SviluppoCompletoDto;
import com.giancotsu.owar.entity.player.Attivita;
import com.giancotsu.owar.entity.player.PlayerBasicInformationEntity;
import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.service.player.CostiService;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.PlayerSviluppoService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerSviluppoService playerSviluppoService;
    private final RisorseService risorseService;
    private final CostiService costiService;



    public PlayerController(PlayerService playerService, PlayerSviluppoService playerSviluppoService, RisorseService risorseService, CostiService costiService) {
        this.playerService = playerService;
        this.playerSviluppoService = playerSviluppoService;
        this.risorseService = risorseService;
        this.costiService = costiService;
    }

    @GetMapping()
    public ResponseEntity<PlayerEntity> getPlayer(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getPlayer(bearerToken);
    }

    @GetMapping(value = "basic")
    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformation(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getPlayerBasicInformation(bearerToken);
    }

    @GetMapping(value = "basic/{nickname}")
    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformationByNickname(@PathVariable("nickname") String nickname) {
        return this.playerService.getPlayerBasicInformationByNickname(nickname);
    }

    @GetMapping(value = "registro-attivita")
    public ResponseEntity<List<Attivita>> getRegistroAttivita(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getRegistroAttivita(bearerToken);
    }

    @GetMapping(value = "registro-attivita/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<Attivita>> getRegistroAttivitaPageable(@RequestHeader("Authorization") String bearerToken, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
        return this.playerService.getRegistroAttivitaPageable(bearerToken, pageNumber, pageSize);
    }

    @GetMapping(value = "risorse")
    public ResponseEntity<List<RisorsaDto>> getPlayerRisorse(@RequestHeader("Authorization") String bearerToken) {
        return this.risorseService.getPlayerResourcesDto(bearerToken);
    }

    @GetMapping(value = "strutture")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(@RequestHeader("Authorization") String bearerToken) {
        return this.playerSviluppoService.getAllBasicBuildingsInfo(bearerToken);
    }

    @GetMapping(value = "strutture/{nickname}")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfoByPlayerNickname(@PathVariable("nickname") String nickname) {
        return this.playerSviluppoService.getAllBasicBuildingsInfoByPlayerNickname(nickname);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}")
    public ResponseEntity<SviluppoCompletoDto> getSviluppoDettById(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {
        return this.playerSviluppoService.getSviluppoDettById(sviluppoId, bearerToken);
    }

    @GetMapping(value = "strutture/nome/{nome}")
    public ResponseEntity<SviluppoCompletoDto> getSviluppoDettByName(@RequestHeader("Authorization") String bearerToken, @PathVariable("nome") String nomeStruttura) {
        return this.playerSviluppoService.getSviluppoDettByName(nomeStruttura, bearerToken);
    }

    @GetMapping(value = "strutture/produzione")
    public ResponseEntity<List<ProduzioneRisorseDto>> getProduzione(@RequestHeader("Authorization") String bearerToken) {
        return this.risorseService.getProductionResourcesDto(bearerToken);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}/alzalivello")
    public ResponseEntity<String> provaAlzaLivello(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {
        return this.playerService.provaAlzaLivello(sviluppoId, bearerToken);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}/chance")
    public ResponseEntity<String> getChance(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {
        return this.playerService.getChance(sviluppoId, bearerToken);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}/canpay")
    public ResponseEntity<Boolean> canPay(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {

        boolean canPay = this.costiService.canPay(sviluppoId, bearerToken);
        if(canPay) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}























