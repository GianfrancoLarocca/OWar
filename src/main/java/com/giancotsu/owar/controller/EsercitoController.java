package com.giancotsu.owar.controller;

import com.giancotsu.owar.dto.AllEsercitoBasicInfoDto;
import com.giancotsu.owar.dto.SoldatoDettagliDto;
import com.giancotsu.owar.dto.UnitaComprateEsercito;
import com.giancotsu.owar.service.player.EsercitoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/player/esercito")
public class EsercitoController {

    private final EsercitoService esercitoService;

    public EsercitoController(EsercitoService esercitoService) {
        this.esercitoService = esercitoService;
    }

    // Attacco
    @GetMapping(value = "attacco/basic")
    public ResponseEntity<List<AllEsercitoBasicInfoDto>> getAllBasicBuildingsInfoByPlayerId(@RequestHeader("Authorization") String bearerToken) {
        return this.esercitoService.getAllBasicBuildingsInfo(bearerToken);
    }

    @GetMapping(value = "attacco/id/{id}")
    public ResponseEntity<SoldatoDettagliDto> getSoldatoDettagliById(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long idSoldato) {
        return this.esercitoService.getSoldatoDettagliBySoldatoId(idSoldato, bearerToken);
    }

    @GetMapping(value = "attacco/canPay/soldato/{id}/quantita/{numeroSoldati}")
    public ResponseEntity<Boolean> canPay(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long soldatoId, @PathVariable("numeroSoldati") int numeroSoldatiDaComprare) {
        return this.esercitoService.canPayResponse(bearerToken, soldatoId, numeroSoldatiDaComprare);
    }

    @GetMapping(value = "attacco/payment/soldato/{id}/quantita/{numeroSoldati}")
    public ResponseEntity<List<UnitaComprateEsercito>> payment(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long soldatoId, @PathVariable("numeroSoldati") String numeroSoldatiDaComprare) {
        return this.esercitoService.payment(bearerToken, soldatoId, numeroSoldatiDaComprare);
    }

    @GetMapping(value = "attacco/coda/addestramento")
    public ResponseEntity<List<UnitaComprateEsercito>> getCodaAddestramento (@RequestHeader("Authorization") String bearerToken) {
        return this.esercitoService.getCodaAddestramento(bearerToken);
    }







}
