package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.AllEsercitoBasicInfoDto;
import com.giancotsu.owar.dto.SoldatoDettagliDto;
import com.giancotsu.owar.dto.UnitaComprateEsercito;
import com.giancotsu.owar.entity.player.*;
import com.giancotsu.owar.entity.player.sviluppo.Arsenale;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.BasicEsercitoInfoProjection;
import com.giancotsu.owar.projection.SoldatoCostiProjection;
import com.giancotsu.owar.repository.player.*;
import com.giancotsu.owar.utils.JwtUserUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EsercitoService {

    private final SoldatoRepository soldatoRepository;
    private final DatiSoldatoRepository datiSoldatoRepository;
    private final PlayerRepository playerRepository;
    private final UnitaRimanentiCodaRepository unitaRimanentiCodaRepository;
    private final ArsenaleRepository arsenaleRepository;
    private final PlayerArsenaleRepository playerArsenaleRepository;
    private final RisorseService risorseService;
    private final JwtUserUtils jwtUserUtils;
    private final TaskScheduler taskScheduler;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private List<DatiSoldato> datiSoldato = new ArrayList<>();
    private Map<Long, List<UnitaComprateEsercito>> codaAddestramento;
    private long codaTempoTotale;

    private String tipo = "esercito";


    public EsercitoService(SoldatoRepository soldatoRepository, DatiSoldatoRepository datiSoldatoRepository, PlayerRepository playerRepository, UnitaRimanentiCodaRepository unitaRimanentiCodaRepository, ArsenaleRepository arsenaleRepository, PlayerArsenaleRepository playerArsenaleRepository, RisorseService risorseService, JwtUserUtils jwtUserUtils, TaskScheduler taskScheduler, SimpMessagingTemplate simpMessagingTemplate) {
        this.soldatoRepository = soldatoRepository;
        this.datiSoldatoRepository = datiSoldatoRepository;
        this.playerRepository = playerRepository;
        this.unitaRimanentiCodaRepository = unitaRimanentiCodaRepository;
        this.arsenaleRepository = arsenaleRepository;
        this.playerArsenaleRepository = playerArsenaleRepository;
        this.risorseService = risorseService;
        this.jwtUserUtils = jwtUserUtils;
        this.taskScheduler = taskScheduler;
        this.simpMessagingTemplate = simpMessagingTemplate;

        this.creaEsercito();
        this.riscattaUnitaInCoda();
    }

    private void creaEsercito() {

        // Mischia I

        String nome1 = "Mischia I";
        Optional<DatiSoldato> optionalMischia1 = datiSoldatoRepository.findByNome(nome1);
        Map<RisorseEnum, Double> costiMischia1 = new HashMap<>();
        if(optionalMischia1.isEmpty()) {

            String descrizione = String.format("La %s è una pistola semi-automatica di ultima generazione, progettata per offrire prestazioni eccellenti in situazioni di alta pressione. " +
                    "Costruita con materiali leggeri e resistenti, la P070 è la scelta ideale per chi cerca affidabilità e precisione sul campo.", nome1);

            String urlImg = "../../assets/img/soldati/mischia1.png";

            costiMischia1.put(RisorseEnum.MICROCHIP, 150.2);
            costiMischia1.put(RisorseEnum.METALLO, 180.2);
            costiMischia1.put(RisorseEnum.ENERGIA, 15.3);
            costiMischia1.put(RisorseEnum.CIVILI, 10.1);
            costiMischia1.put(RisorseEnum.BITCOIN, 100.9);

            int secondiPerAddestrare = 2;

            DatiSoldato soldato = new DatiSoldato(nome1, descrizione, urlImg, secondiPerAddestrare, costiMischia1, "P070");

            this.datiSoldato.add(datiSoldatoRepository.save(soldato));
        } else {
            this.datiSoldato.add(optionalMischia1.get());
        }

        // Mischia II

        String nome2 = "Mischia II";
        Optional<DatiSoldato> optionalMischia2 = datiSoldatoRepository.findByNome(nome2);
        Map<RisorseEnum, Double> costiMischia2 = new HashMap<>();
        if(optionalMischia2.isEmpty()) {

            String descrizione = String.format("La %s è una pistola semi-automatica di ultima generazione, progettata per offrire prestazioni eccellenti in situazioni di alta pressione. " +
                    "Costruita con materiali leggeri e resistenti, la P070 è la scelta ideale per chi cerca affidabilità e precisione sul campo.", nome2);

            String urlImg = "../../assets/img/soldati/mischia2.png";

            costiMischia2.put(RisorseEnum.MICROCHIP, 134.2);
            costiMischia2.put(RisorseEnum.METALLO, 280.2);
            costiMischia2.put(RisorseEnum.ENERGIA, 143.3);
            costiMischia2.put(RisorseEnum.CIVILI, 124.1);
            costiMischia2.put(RisorseEnum.BITCOIN, 110.9);

            int secondiPerAddestrare = 6;

            DatiSoldato soldato = new DatiSoldato(nome2, descrizione, urlImg, secondiPerAddestrare, costiMischia2, "TT9000");

            this.datiSoldato.add(datiSoldatoRepository.save(soldato));
        } else {
            this.datiSoldato.add(optionalMischia2.get());
        }

        // Mischia III

        String nome3 = "Mischia III";
        Optional<DatiSoldato> optionalMischia3 = datiSoldatoRepository.findByNome(nome3);
        Map<RisorseEnum, Double> costiMischia3 = new HashMap<>();
        if(optionalMischia3.isEmpty()) {

            String descrizione = String.format("La %s è una pistola semi-automatica di ultima generazione, progettata per offrire prestazioni eccellenti in situazioni di alta pressione. " +
                    "Costruita con materiali leggeri e resistenti, la P070 è la scelta ideale per chi cerca affidabilità e precisione sul campo.", nome3);

            String urlImg = "../../assets/img/soldati/mischia3.png";

            costiMischia3.put(RisorseEnum.MICROCHIP, 134.2);
            costiMischia3.put(RisorseEnum.METALLO, 180.2);
            costiMischia3.put(RisorseEnum.ENERGIA, 143.3);
            costiMischia3.put(RisorseEnum.CIVILI, 124.1);
            costiMischia3.put(RisorseEnum.BITCOIN, 110.9);

            int secondiPerAddestrare = 10;

            DatiSoldato soldato = new DatiSoldato(nome3, descrizione, urlImg, secondiPerAddestrare, costiMischia3, "S99K");

            this.datiSoldato.add(datiSoldatoRepository.save(soldato));
        } else {
            this.datiSoldato.add(optionalMischia3.get());
        }






    }

    private void riscattaUnitaInCoda() {

        Set<Long> playerIds = playerRepository.getAllPlayerId();
        if (!playerIds.isEmpty()) {
            for (Long playerId : playerIds) {
                Optional<List<UnitaRimanentiCoda>> urcOpt = unitaRimanentiCodaRepository.findByPlayerId(playerId, this.tipo);
                if (urcOpt.isPresent()) {
                    List<UnitaRimanentiCoda> urcList = urcOpt.get();

                    urcList.forEach(elem -> {

                        Optional<Soldato> optionalSoldato = soldatoRepository.findSoldatoByPlayerIdAndDatiSoldatoId(playerId, elem.getIdUnita());

                        if(optionalSoldato.isPresent()) {

                            long numeroSoldati = optionalSoldato.get().getQuantitaSoldati() + elem.getQuantita();

                            optionalSoldato.get().setQuantitaSoldati(numeroSoldati);

                            soldatoRepository.save(optionalSoldato.get());
                            unitaRimanentiCodaRepository.deleteByPlayerIdAndUnitaIdAndTipo(playerId, elem.getIdUnita(), this.tipo);
                        }

                    });


                }
            }
        }

    }

    public void salvaSoldati(UserEntity user) {

        for (DatiSoldato ds : this.datiSoldato) {
            Soldato soldato = new Soldato();

            soldato.setPlayer(user.getPlayer());
            soldato.setDatiSoldato(ds);

            soldato.setSecondiPerAddestrare(ds.getSecondiPerAddestrare());
            soldato.setQuantitaSoldati(0);
            soldato.setQuantitaSoldatiCoda(0);

            soldatoRepository.save(soldato);
        }
    }

    public ResponseEntity<List<AllEsercitoBasicInfoDto>> getAllBasicBuildingsInfo (String bearerToken) {
        return this.getAllBasicBuildingsInfoByPlayerId(jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
    }

    private ResponseEntity<List<AllEsercitoBasicInfoDto>> getAllBasicBuildingsInfoByPlayerId (long playerId) {

        List<AllEsercitoBasicInfoDto> esercitoBasicInfo = new ArrayList<>();
        List<BasicEsercitoInfoProjection> esercitoBasicInfoProj = soldatoRepository.getAllEsercitoBasicInfo(playerId);

        esercitoBasicInfoProj.forEach(soldato -> {
            AllEsercitoBasicInfoDto s = new AllEsercitoBasicInfoDto();
            s.setId(soldato.getId());
            s.setNome(soldato.getNome());
            s.setSoldati(soldato.getSoldati());
            s.setUrlImg(soldato.getUrl());
            esercitoBasicInfo.add(s);
        });
        return new ResponseEntity<>(esercitoBasicInfo, HttpStatus.OK);
    }

    public Map<String, Double> getCostiSoldato(Long playerId, Long soldatoId) {

        List<SoldatoCostiProjection> costiProjections = datiSoldatoRepository.getCosti(playerId, soldatoId);
        Map<String, Double> costi = new HashMap<>();
        costiProjections.forEach(record -> {
            costi.put(record.getRisorsa(), record.getCosto());
        });

        return costi;
    }

    private PlayerArsenale getPlayerArsenaleByNomeArma(String nomeArma, long playerId) {

        Optional<Arsenale> arsenaleOptional = this.arsenaleRepository.findByNome(nomeArma);
        Arsenale arsenale;
        if(arsenaleOptional.isPresent()) {
            arsenale = arsenaleOptional.get();

            Optional<PlayerArsenale> playerArsenaleOptional = this.playerArsenaleRepository.findPlayerArsenaleByPlayerIdAndArsenaleId(playerId, arsenale.getId());

            if (playerArsenaleOptional.isPresent()) {
                return playerArsenaleOptional.get();
            } else {
                throw  new RuntimeException("Player non trovato");
            }
        } else {
            throw  new RuntimeException("Arma non trovata");
        }
    }

    private PlayerArsenale getPlayerArsenaleBySoldatoId(long soldatoId, long playerId) {

        Optional<DatiSoldato> optionalDatiSoldato = datiSoldatoRepository.findById(soldatoId);

        DatiSoldato datiSoldato;
        if (optionalDatiSoldato.isPresent()) {
            datiSoldato = optionalDatiSoldato.get();
        } else {
            throw new RuntimeException("Dati soldato non trovati");
        }

        Optional<Arsenale> arsenaleOptional = this.arsenaleRepository.findByNome(datiSoldato.getArmaSoldato());
        Arsenale arsenale;
        if(arsenaleOptional.isPresent()) {
            arsenale = arsenaleOptional.get();

            Optional<PlayerArsenale> playerArsenaleOptional = this.playerArsenaleRepository.findPlayerArsenaleByPlayerIdAndArsenaleId(playerId, arsenale.getId());

            if (playerArsenaleOptional.isPresent()) {
                return playerArsenaleOptional.get();
            } else {
                throw  new RuntimeException("Player non trovato");
            }
        } else {
            throw  new RuntimeException("Arma non trovata");
        }
    }

    public ResponseEntity<SoldatoDettagliDto> getSoldatoDettagliBySoldatoId(Long soldatoId, String bearerToken) {

        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();

        Optional<Soldato> optionalSoldato = soldatoRepository.findSoldatoByPlayerIdAndDatiSoldatoId(playerId, soldatoId);

        Soldato soldato;
        if (optionalSoldato.isPresent()) {
            soldato = optionalSoldato.get();
        } else {
            throw new RuntimeException("Player non trovato");
        }

        Optional<DatiSoldato> optionalDatiSoldato = datiSoldatoRepository.findById(soldatoId);

        DatiSoldato datiSoldato;
        if (optionalDatiSoldato.isPresent()) {
            datiSoldato = optionalDatiSoldato.get();
        } else {
            throw new RuntimeException("Dati soldato non trovati");
        }

        SoldatoDettagliDto soldatoDettagliDto = new SoldatoDettagliDto();

        soldatoDettagliDto.setId(datiSoldato.getId());
        soldatoDettagliDto.setNome(datiSoldato.getNome());
        soldatoDettagliDto.setDescrizione(datiSoldato.getDescrizione());
        soldatoDettagliDto.setQuantitaSoldati(soldato.getQuantitaSoldati());
        soldatoDettagliDto.setUrlImmagine(datiSoldato.getUrlImmagine());
        soldatoDettagliDto.setSecondiPerAddestrare(soldato.getSecondiPerAddestrare());
        soldatoDettagliDto.setArmaSoldato(datiSoldato.getArmaSoldato());

        soldatoDettagliDto.setCosti(this.getCostiSoldato(playerId, soldatoId));

        PlayerArsenale pa = this.getPlayerArsenaleByNomeArma(datiSoldato.getArmaSoldato(), playerId);

        soldatoDettagliDto.setLimiteUnita(pa.getNumeroMassimoArma());

        return new ResponseEntity<>(soldatoDettagliDto, HttpStatus.OK);

    }

    private boolean canPay(long playerId, long soldatoId, long numeroSoldatiDaComprare) {

        Map<String, Double> playerResources = risorseService.getPlayerResources(playerId);
        Map<String, Double> costiSoldato = this.getCostiSoldato(playerId, soldatoId);

        if(numeroSoldatiDaComprare < 1) {
            throw new RuntimeException("Il numero minimo di soldati da addestrare è 1");
        }

        for (String risorsa : costiSoldato.keySet()) {
            if (playerResources.get(risorsa) < costiSoldato.get(risorsa) * numeroSoldatiDaComprare) {
                return false;
            }
        }
        return true;
    }

    public ResponseEntity<Boolean> canPayResponse(String bearerToken, Long soldatoId, long numeroSoldatiDaComprare) {

        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();

        boolean canPay = this.canPay(playerId, soldatoId, numeroSoldatiDaComprare);

        return new ResponseEntity<>(canPay, HttpStatus.OK);

    }

    @PostConstruct
    public void init() {
        this.codaAddestramento = new HashMap<>();
        this.codaTempoTotale = System.currentTimeMillis();
    }

    public ResponseEntity<List<UnitaComprateEsercito>> payment(String bearerToken, Long soldatoId, String numeroSoldati) {

        long numeroSoldatiDaComprare;

        try {
            numeroSoldatiDaComprare = Long.parseLong(numeroSoldati);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Valore non valido");
        }

        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(playerId);
        PlayerEntity player;
        if (optionalPlayer.isPresent()) {
            player = optionalPlayer.get();
        } else {
            throw new RuntimeException("Player non trovato");
        }

        Optional<Soldato> optionalSoldato = soldatoRepository.findSoldatoByPlayerIdAndDatiSoldatoId(playerId, soldatoId);
        Soldato soldato;
        if(optionalSoldato.isPresent()) {
            soldato = optionalSoldato.get();
        } else {
            throw new RuntimeException("Player non trovato");
        }

        // verifiche

        if (numeroSoldatiDaComprare > 999999999) {
            throw new RuntimeException("Il massimo di soldati che puoi addestrare è: 999.999.999");
        }

        if(!this.canPay(playerId, soldatoId, numeroSoldatiDaComprare)) {
            throw new RuntimeException("Non hai le risorse");
        }

        if (this.codaAddestramento == null || this.codaAddestramento.isEmpty() || !this.codaAddestramento.containsKey(playerId) || this.codaAddestramento.get(playerId).isEmpty()) {
            this.codaAddestramento.put(playerId, new ArrayList<>());
        }

        int slotCoda = player.getBasicInformation().getSlotCodaEsercito();
        if(this.codaAddestramento.get(playerId).size() >= slotCoda ) {
            throw new RuntimeException("Coda piena");
        }

        PlayerArsenale pa = this.getPlayerArsenaleBySoldatoId(soldatoId, playerId);
        System.err.println("Numero massimo arma: " + pa.getNumeroMassimoArma());
        long numeroMassimoArma = pa.getNumeroMassimoArma();

        long quantitaSoldati = soldato.getQuantitaSoldati();
        System.err.println("Quantita soldati: " + quantitaSoldati);

        long numeroUnitaInCoda = numeroSoldatiDaComprare;
        for (UnitaComprateEsercito elemento : this.codaAddestramento.get(playerId)) {
            if (elemento.getSoldatoId() == soldatoId) {
                System.err.println("id soldato: " + elemento.getSoldatoId() + " - soldati da comprare: " + elemento.getNumeroSoldatiDaComprare());
                numeroUnitaInCoda += elemento.getNumeroSoldatiDaComprare();
            }
        }
        System.err.println("Numero soldati in coda: " + numeroUnitaInCoda);

        System.err.println("Totale soldati posseduti: " + (numeroUnitaInCoda + quantitaSoldati));
        System.err.println("Limite - soldati posseduti: " + (numeroMassimoArma - (numeroUnitaInCoda + quantitaSoldati)));

        if (numeroMassimoArma - (numeroUnitaInCoda + quantitaSoldati) < 0 ) {
            throw new RuntimeException("Hai superato il limite, il massimo di unità che puoi addestrare è: " + (numeroMassimoArma - ((numeroUnitaInCoda - numeroSoldatiDaComprare) + quantitaSoldati)));
        }

        // sottrarre le risorse

        Map<String, Double> costiSoldato = this.getCostiSoldato(playerId, soldatoId);
        for (Map.Entry<String, Double> costi: costiSoldato.entrySet()) {
            costiSoldato.put(costi.getKey(), costi.getValue() * numeroSoldatiDaComprare);
        }

        risorseService.payment(costiSoldato, playerId);


        // aggiungere i soldati

        UnitaComprateEsercito uce = new UnitaComprateEsercito(soldatoId, numeroSoldatiDaComprare, soldato.getSecondiPerAddestrare());

        long tempoInMilli = uce.getNumeroSoldatiDaComprare() * uce.getSecondiAddestramento() * 1000;
        if (this.codaAddestramento.isEmpty() || this.codaAddestramento.get(playerId).isEmpty() ) {
            uce.setTempoRimanenteMillisecondi(System.currentTimeMillis() + tempoInMilli);
        } else {
            uce.setTempoRimanenteMillisecondi(this.codaTempoTotale + tempoInMilli);
        }

        if (this.codaTempoTotale < System.currentTimeMillis()) {
            this.codaTempoTotale = System.currentTimeMillis() + tempoInMilli;
        } else {
            this.codaTempoTotale = this.codaTempoTotale + tempoInMilli;
        }

        uce.setTempoRimanenteTotaleMillisecondi(this.codaTempoTotale);

        List<UnitaComprateEsercito> uceList;
        if (codaAddestramento.get(playerId) == null) {
            uceList = new LinkedList<>();
        } else {
            uceList = codaAddestramento.get(playerId);
        }

        uceList.add(uce);
        this.codaAddestramento.put(playerId, uceList);

        List<UnitaRimanentiCoda> urcList;
        if(player.getUnitaRimanentiCoda() == null) {
            urcList = new ArrayList<>();
        } else {
            urcList = player.getUnitaRimanentiCoda();
        }

        UnitaRimanentiCoda urc;
        Optional<UnitaRimanentiCoda> urcOpt = unitaRimanentiCodaRepository.findByPlayerIdAndUnitaId(playerId, uce.getSoldatoId(), this.tipo);
        if (urcOpt.isPresent()) {
            urc = urcOpt.get();
            long quantita = urc.getQuantita();
            urc.setQuantita(quantita + uce.getNumeroSoldatiDaComprare());
        } else{
            urc = new UnitaRimanentiCoda();
            urc.setTipo(this.tipo);
            urc.setIdUnita(uce.getSoldatoId());
            urc.setQuantita(uce.getNumeroSoldatiDaComprare());
            urc.setPlayer(player);
        }

        urcList.add(urc);

        player.setUnitaRimanentiCoda(urcList);
        playerRepository.save(player);

        if (this.codaAddestramento.get(playerId).size() == 1 && this.codaAddestramento.get(playerId).get(0).getNumeroSoldatiDaComprare() > 0) {
            this.addestraSoldato(playerId, uce);
        }

        return new ResponseEntity<>(this.codaAddestramento.get(playerId), HttpStatus.OK);

    }

    private synchronized  void addestraSoldato(long playerId, UnitaComprateEsercito uce) {

        taskScheduler.schedule(
                () -> completaAddestramento(playerId, uce),
                LocalDateTime.now().plusSeconds(uce.getSecondiAddestramento()).atZone(ZoneId.systemDefault()).toInstant()
        );

    }

    private void completaAddestramento(long playerId, UnitaComprateEsercito uce) {

        Optional<Soldato> optionalSoldato = soldatoRepository.findSoldatoByPlayerIdAndDatiSoldatoId(playerId, uce.getSoldatoId());

        if(optionalSoldato.isPresent()) {

            long numeroSoldati = optionalSoldato.get().getQuantitaSoldati() + 1;

            optionalSoldato.get().setQuantitaSoldati(numeroSoldati);

            soldatoRepository.save(optionalSoldato.get());

            uce.setNumeroSoldatiDaComprare(uce.getNumeroSoldatiDaComprare()-1);

            this.sendQueueToClient(playerId);

            Optional<UnitaRimanentiCoda> urcOpt = unitaRimanentiCodaRepository.findByPlayerIdAndUnitaId(playerId, uce.getSoldatoId(), this.tipo);
            if(urcOpt.isPresent()) {
                UnitaRimanentiCoda urc = urcOpt.get();
                urc.setQuantita(urc.getQuantita() - 1);
                unitaRimanentiCodaRepository.save(urc);
            }

        } else {
            throw new RuntimeException("Player non trovato");
        }

        System.err.println("SOLDATO CON ID: " + uce.getSoldatoId() + " ADDESTRATO, IN CODA ANCORA " + uce.getNumeroSoldatiDaComprare() + " SOLDATI. Tempo = " + uce.getSecondiAddestramento());

        if (this.codaAddestramento.get(playerId).get(0).getNumeroSoldatiDaComprare() < 1) {
            codaAddestramento.get(playerId).remove(0);
            if(!this.codaAddestramento.get(playerId).isEmpty() && this.codaAddestramento.get(playerId).get(0).getNumeroSoldatiDaComprare() > 0) {

                this.addestraSoldato(playerId, this.codaAddestramento.get(playerId).get(0));
            }
        } else {
            this.addestraSoldato(playerId, uce);
        }

    }

    public ResponseEntity<List<UnitaComprateEsercito>> getCodaAddestramento (String bearerToken) {

        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();

        if (this.codaAddestramento != null) {
            return new ResponseEntity<>(this.codaAddestramento.get(playerId), HttpStatus.OK);
        } else {
            throw new RuntimeException("Coda vuota");
        }
    }

    public void sendQueueToClient(long playerId) {
        String destination = "/topic/soldati/" + playerId;
        simpMessagingTemplate.convertAndSend(destination, this.codaAddestramento.get(playerId));
    }


}
























