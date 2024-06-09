package com.giancotsu.owar.controller;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.SviluppoStrutturaCompletoDto;
import com.giancotsu.owar.service.player.CostiService;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.StruttureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/player/sviluppo")
public class SviluppoController {

    private final StruttureService struttureService;
    private final CostiService costiService;
    private final PlayerService playerService;

    public SviluppoController(StruttureService struttureService, CostiService costiService, PlayerService playerService) {
        this.struttureService = struttureService;
        this.costiService = costiService;
        this.playerService = playerService;
    }

    @GetMapping(value = "strutture")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(@RequestHeader("Authorization") String bearerToken) {
        return this.struttureService.getAllBasicBuildingsInfo(bearerToken);
    }

    @GetMapping(value = "strutture/id/{id}")
    public ResponseEntity<SviluppoStrutturaCompletoDto> getSviluppoDettByName(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long idStruttura) {
        return this.struttureService.getSviluppoStrutturaDettById(idStruttura, bearerToken);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}/canpay")
    public ResponseEntity<Boolean> canPay(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoStrutturaId) {

        boolean canPay = this.costiService.canPayStrutture(sviluppoStrutturaId, bearerToken);
        if(canPay) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping(value = "strutture/id/{sviluppoId}/alzalivello")
    public ResponseEntity<String> provaAlzaLivello(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {
        return this.playerService.provaAlzaLivelloSviluppoStrutture(sviluppoId, bearerToken);
    }

}
