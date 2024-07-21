package com.giancotsu.owar.controller;

import com.giancotsu.owar.dto.*;
import com.giancotsu.owar.service.player.*;
import com.giancotsu.owar.utils.JwtUserUtils;
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
    private final ArsenaleService arsenaleService;
    private final DifesaService difesaService;
    private final JwtUserUtils jwtUserUtils;

    public SviluppoController(StruttureService struttureService, CostiService costiService, PlayerService playerService, TecnologiaService tecnologiaService, ArsenaleService arsenaleService, DifesaService difesaService, JwtUserUtils jwtUserUtils) {
        this.struttureService = struttureService;
        this.costiService = costiService;
        this.playerService = playerService;
        this.tecnologiaService = tecnologiaService;
        this.arsenaleService = arsenaleService;
        this.difesaService = difesaService;
        this.jwtUserUtils = jwtUserUtils;
    }

    //Strutture
    @GetMapping(value = "strutture")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(@RequestHeader("Authorization") String bearerToken) {
        return this.struttureService.getAllBasicBuildingsInfo(bearerToken);
    }

    @GetMapping(value = "strutture/id/{id}")
    public ResponseEntity<SviluppoStrutturaCompletoDto> getSviluppoDettById(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long idStruttura) {
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
    public ResponseEntity<SviluppoTecnologiaDettagliDto> getTechDettById(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long idTech) {
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

    //Arsenale
    @GetMapping(value = "arsenale")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicArsenaleInfo(@RequestHeader("Authorization") String bearerToken) {
        return this.arsenaleService.getAllBasicBuildingsInfo(bearerToken);
    }

    @GetMapping(value = "arsenale/id/{id}")
    public ResponseEntity<SviluppoArsenaleDettagliDto> getArsenaleDettById(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long arsenaleId) {
        return this.arsenaleService.getSviluppoArsenaleDettById(arsenaleId, bearerToken);
    }

    @GetMapping(value = "arsenale/id/{arsenaleId}/canpay")
    public ResponseEntity<Boolean> canPayArsenale(@RequestHeader("Authorization") String bearerToken, @PathVariable("arsenaleId") Long arsenaleId) {

        long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        boolean canPay = this.costiService.canPayNew(bearerToken, costiService.getCostiSviluppoArsenale(arsenaleId, playerId));
        if(canPay) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping(value = "arsenale/id/{arsenaleId}/alzalivello")
    public ResponseEntity<String> provaAlzaLivelloArsenale(@RequestHeader("Authorization") String bearerToken, @PathVariable("arsenaleId") Long arsenaleId) {
        return this.playerService.provaAlzaLivelloSviluppoArsenale(arsenaleId, bearerToken);
    }

    @GetMapping(value = "arsenale/requisito")
    public ResponseEntity<Integer> getFabbricaLvl(@RequestHeader("Authorization") String bearerToken) {
        return this.arsenaleService.getFabbricaLvl(bearerToken);
    }

    //Difesa
    @GetMapping(value = "difesa")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicDifesaInfo(@RequestHeader("Authorization") String bearerToken) {
        return this.difesaService.getAllBasicBuildingsInfo(bearerToken);
    }

    @GetMapping(value = "difesa/id/{id}")
    public ResponseEntity<SviluppoDifesaDettagliDto> getDifesaDettById(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long difesaId) {
        return this.difesaService.getSviluppoDifesaDettById(difesaId, bearerToken);
    }

    @GetMapping(value = "difesa/id/{difesaId}/canpay")
    public ResponseEntity<Boolean> canPayDifesa(@RequestHeader("Authorization") String bearerToken, @PathVariable("difesaId") Long difesaId) {

        long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        boolean canPay = this.costiService.canPayNew(bearerToken, costiService.getCostiSviluppoDifesa(difesaId, playerId));
        if(canPay) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping(value = "difesa/id/{difesaId}/alzalivello")
    public ResponseEntity<String> provaAlzaLivelloDifesa(@RequestHeader("Authorization") String bearerToken, @PathVariable("difesaId") Long difesaId) {
        return this.playerService.provaAlzaLivelloSviluppoDifesa(difesaId, bearerToken);
    }


























}
