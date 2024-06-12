package com.giancotsu.owar.controller;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.SviluppoStrutturaCompletoDto;
import com.giancotsu.owar.dto.SviluppoTecnologiaDettagliDto;
import com.giancotsu.owar.service.player.CostiService;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.StruttureService;
import com.giancotsu.owar.service.player.TecnologiaService;
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
    private final TecnologiaService tecnologiaService;

    public SviluppoController(StruttureService struttureService, CostiService costiService, PlayerService playerService, TecnologiaService tecnologiaService) {
        this.struttureService = struttureService;
        this.costiService = costiService;
        this.playerService = playerService;
        this.tecnologiaService = tecnologiaService;
    }

    //Strutture
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

    //Tecnologia
    @GetMapping(value = "tecnologia")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicTechInfo(@RequestHeader("Authorization") String bearerToken) {
        return this.tecnologiaService.getAllBasicBuildingsInfo(bearerToken);
    }

    @GetMapping(value = "tecnologia/id/{id}")
    public ResponseEntity<SviluppoTecnologiaDettagliDto> getTechDettByName(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long idTech) {
        return this.tecnologiaService.getSviluppoStrutturaDettById(idTech, bearerToken);
    }

    @GetMapping(value = "tecnologia/id/{techId}/canpay")
    public ResponseEntity<Boolean> canPayTech(@RequestHeader("Authorization") String bearerToken, @PathVariable("techId") Long techId) {

        boolean canPay = this.costiService.canPayTech(techId, bearerToken);
        if(canPay) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping(value = "tecnologia/id/{techId}/alzalivello")
    public ResponseEntity<String> provaAlzaLivelloTech(@RequestHeader("Authorization") String bearerToken, @PathVariable("techId") Long techId) {
        return this.playerService.provaAlzaLivelloSviluppoTech(techId, bearerToken);
    }

    @GetMapping(value = "tecnologia/requisito")
    public ResponseEntity<Integer> getLabLvl(@RequestHeader("Authorization") String bearerToken){
        return this.tecnologiaService.getLabLvl(bearerToken);
    }


}
