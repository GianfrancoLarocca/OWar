package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.SviluppoArsenaleDettagliDto;
import com.giancotsu.owar.dto.SviluppoTecnologiaDettagliDto;
import com.giancotsu.owar.entity.player.PlayerArsenale;
import com.giancotsu.owar.entity.player.sviluppo.Arsenale;
import com.giancotsu.owar.entity.player.sviluppo.Tecnologia;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.repository.player.ArsenaleRepository;
import com.giancotsu.owar.repository.player.PlayerArsenaleRepository;
import com.giancotsu.owar.repository.player.StruttureRepository;
import com.giancotsu.owar.utils.JwtUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArsenaleService {

    private final ArsenaleRepository arsenaleRepository;
    private final PlayerArsenaleRepository playerArsenaleRepository;
    private final StruttureRepository struttureRepository;
    private final AlzaLivelloTry alzaLivelloTry;
    private final CostiService costiService;
    private final JwtUserUtils jwtUserUtils;

    public ArsenaleService(ArsenaleRepository arsenaleRepository, PlayerArsenaleRepository playerArsenaleRepository, StruttureRepository struttureRepository, AlzaLivelloTry alzaLivelloTry, CostiService costiService, JwtUserUtils jwtUserUtils) {
        this.arsenaleRepository = arsenaleRepository;
        this.playerArsenaleRepository = playerArsenaleRepository;
        this.struttureRepository = struttureRepository;
        this.alzaLivelloTry = alzaLivelloTry;
        this.costiService = costiService;
        this.jwtUserUtils = jwtUserUtils;

        this.creaArsenale();
    }

    private List<Arsenale> arsenale = new ArrayList<>();

    private void creaArsenale() {

        // P070

        String nome1 = "P070";
        Optional<Arsenale> optionalPistola = arsenaleRepository.findByNome(nome1);
        Map<RisorseEnum, Double> costiPistola = new HashMap<>();
        if(optionalPistola.isEmpty()) {
            String descrizione = String.format("La \"%s\" è una pistola semi-automatica di ultima generazione, progettata per offrire prestazioni eccellenti in situazioni di alta pressione. Costruita con materiali leggeri e resistenti, la P070 è la scelta ideale per chi cerca affidabilità e precisione sul campo.", nome1);
            costiPistola.put(RisorseEnum.MICROCHIP, 150.2);
            costiPistola.put(RisorseEnum.METALLO, 380.2);
            costiPistola.put(RisorseEnum.ENERGIA, 15.3);
            costiPistola.put(RisorseEnum.CIVILI, 10.1);
            costiPistola.put(RisorseEnum.BITCOIN, 300.9);
            int livelloFabbricaRequisito = 1;
            long attacco = 5;
            long armatura = 2;
            long vita = 3;
            long velocita = 15;
            long stiva = 0;
            long consumo = 0;
            Arsenale p070 = new Arsenale(null, nome1, descrizione, "../../assets/img/sviluppo-arsenale/pistol.png", 1.14, costiPistola, livelloFabbricaRequisito, attacco, armatura, vita, velocita, stiva, consumo);

            this.arsenale.add(arsenaleRepository.save(p070));
        } else {
            this.arsenale.add(optionalPistola.get());
        }

        // TT9000

        String nome2 = "TT9000";
        Optional<Arsenale> optionalTT9000 = arsenaleRepository.findByNome(nome2);
        Map<RisorseEnum, Double> costiTT9000 = new HashMap<>();
        if(optionalTT9000.isEmpty()) {
            String descrizione = String.format("La \"%s\" è una mitraglietta compatta e potente, progettata per offrire un'elevata cadenza di fuoco e precisione in ogni situazione. Costruita con materiali all'avanguardia, è la scelta ideale per operazioni tattiche e combattimenti ravvicinati.", nome2);
            costiTT9000.put(RisorseEnum.MICROCHIP, 170.1);
            costiTT9000.put(RisorseEnum.METALLO, 400.1);
            costiTT9000.put(RisorseEnum.ENERGIA, 15.5);
            costiTT9000.put(RisorseEnum.CIVILI, 11.9);
            costiTT9000.put(RisorseEnum.BITCOIN, 415.9);
            int livelloFabbricaRequisito = 3;
            long attacco = 15;
            long armatura = 3;
            long vita = 5;
            long velocita = 13;
            long stiva = 0;
            long consumo = 0;
            Arsenale tt9000 = new Arsenale(null, nome2, descrizione, "../../assets/img/sviluppo-arsenale/machine-gun.png", 1.14, costiTT9000, livelloFabbricaRequisito, attacco, armatura, vita, velocita, stiva, consumo);

            this.arsenale.add(arsenaleRepository.save(tt9000));
        } else {
            this.arsenale.add(optionalTT9000.get());
        }

        // S99K

        String nome3 = "S99K";
        Optional<Arsenale> optionalS99K = arsenaleRepository.findByNome(nome3);
        Map<RisorseEnum, Double> costiS99K = new HashMap<>();
        if(optionalS99K.isEmpty()) {
            String descrizione = String.format("L' \"%s\" è un fucile a pompa robusto e affidabile, progettato per offrire un'elevata potenza di fuoco e versatilità in ogni situazione. Ideale per operazioni tattiche, difesa personale e uso in ambienti ostili.", nome3);
            costiS99K.put(RisorseEnum.MICROCHIP, 140.9);
            costiS99K.put(RisorseEnum.METALLO, 420.8);
            costiS99K.put(RisorseEnum.ENERGIA, 17.7);
            costiS99K.put(RisorseEnum.CIVILI, 13.7);
            costiS99K.put(RisorseEnum.BITCOIN, 450.2);
            int livelloFabbricaRequisito = 4;
            long attacco = 18;
            long armatura = 5;
            long vita = 8;
            long velocita = 10;
            long stiva = 0;
            long consumo = 0;
            Arsenale s99k = new Arsenale(null, nome3, descrizione, "../../assets/img/sviluppo-arsenale/shotgun.png", 1.14, costiS99K, livelloFabbricaRequisito, attacco, armatura, vita, velocita, stiva, consumo);

            this.arsenale.add(arsenaleRepository.save(s99k));
        } else {
            this.arsenale.add(optionalS99K.get());
        }

        // BR88EVO

        String nome4 = "BR88EVO";
        Optional<Arsenale> optionalBR88EVO = arsenaleRepository.findByNome(nome4);
        Map<RisorseEnum, Double> costiBR88EVO = new HashMap<>();
        if(optionalBR88EVO.isEmpty()) {
            String descrizione = String.format("Il \"%s\" è un fucile d'assalto avanzato, progettato per garantire precisione, potenza e affidabilità in ogni missione. Ideale per operazioni militari e tattiche, combina tecnologia moderna con una costruzione robusta.", nome4);
            costiBR88EVO.put(RisorseEnum.MICROCHIP, 199.9);
            costiBR88EVO.put(RisorseEnum.METALLO, 450.8);
            costiBR88EVO.put(RisorseEnum.ENERGIA, 20.8);
            costiBR88EVO.put(RisorseEnum.CIVILI, 18.9);
            costiBR88EVO.put(RisorseEnum.BITCOIN, 550.9);
            int livelloFabbricaRequisito = 8;
            long attacco = 40;
            long armatura = 10;
            long vita = 12;
            long velocita = 8;
            long stiva = 0;
            long consumo = 0;
            Arsenale br88evo = new Arsenale(null, nome4, descrizione, "../../assets/img/sviluppo-arsenale/assoult-riffle.png", 1.14, costiBR88EVO, livelloFabbricaRequisito, attacco, armatura, vita, velocita, stiva, consumo);

            this.arsenale.add(arsenaleRepository.save(br88evo));
        } else {
            this.arsenale.add(optionalBR88EVO.get());
        }

        // AKK164

        String nome5 = "AKK164";
        Optional<Arsenale> optionalAKK164 = arsenaleRepository.findByNome(nome5);
        Map<RisorseEnum, Double> costiAKK164 = new HashMap<>();
        if(optionalAKK164.isEmpty()) {
            String descrizione = String.format("L' \"%s\" è un fucile d'assalto affidabile e potente, progettato per eccellere in condizioni di combattimento difficili. Questo fucile combina la tradizionale robustezza con le innovazioni moderne per garantire precisione e versatilità sul campo di battaglia.", nome5);
            costiAKK164.put(RisorseEnum.MICROCHIP, 230.5);
            costiAKK164.put(RisorseEnum.METALLO, 455.2);
            costiAKK164.put(RisorseEnum.ENERGIA, 22.2);
            costiAKK164.put(RisorseEnum.CIVILI, 20.1);
            costiAKK164.put(RisorseEnum.BITCOIN, 700.1);
            int livelloFabbricaRequisito = 10;
            long attacco = 55;
            long armatura = 11;
            long vita = 15;
            long velocita = 6;
            long stiva = 0;
            long consumo = 0;
            Arsenale akk164 = new Arsenale(null, nome5, descrizione, "../../assets/img/sviluppo-arsenale/assoult-riffle-ak.png", 1.14, costiAKK164, livelloFabbricaRequisito, attacco, armatura, vita, velocita, stiva, consumo);

            this.arsenale.add(arsenaleRepository.save(akk164));
        } else {
            this.arsenale.add(optionalAKK164.get());
        }

        // SP3R

        String nome6 = "SP3R";
        Optional<Arsenale> optionalSP3R = arsenaleRepository.findByNome(nome6);
        Map<RisorseEnum, Double> costiSP3R = new HashMap<>();
        if(optionalSP3R.isEmpty()) {
            String descrizione = String.format("L' \"%s\" è un fucile da cecchino, progettato per colpire bersagli a lunga distanza con letale accuratezza. Realizzato con materiali di alta qualità, offre stabilità e affidabilità in ogni tiro.", nome6);
            costiSP3R.put(RisorseEnum.MICROCHIP, 250.9);
            costiSP3R.put(RisorseEnum.METALLO, 600.9);
            costiSP3R.put(RisorseEnum.ENERGIA, 25.9);
            costiSP3R.put(RisorseEnum.CIVILI, 25.1);
            costiSP3R.put(RisorseEnum.BITCOIN, 950.9);
            int livelloFabbricaRequisito = 15;
            long attacco = 150;
            long armatura = 7;
            long vita = 10;
            long velocita = 3;
            long stiva = 0;
            long consumo = 0;
            Arsenale sp3r = new Arsenale(null, nome6, descrizione, "../../assets/img/sviluppo-arsenale/sniper.png", 1.14, costiSP3R, livelloFabbricaRequisito, attacco, armatura, vita, velocita, stiva, consumo);

            this.arsenale.add(arsenaleRepository.save(sp3r));
        } else {
            this.arsenale.add(optionalSP3R.get());
        }





    }

    public void salvaArsenale(UserEntity user) {

        for (Arsenale t : this.arsenale) {
            PlayerArsenale playerArsenale = new PlayerArsenale();
            playerArsenale.setPlayer(user.getPlayer());
            playerArsenale.setArsenale(t);
            playerArsenaleRepository.save(playerArsenale);
        }
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(String bearerToken) {
        return this.getAllBasicBuildingsInfoByPlayerId(jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfoByPlayerId(long playerId) {
        List<AllBasicBuildingsInfoDto> basicBuildingsInfo = new ArrayList<>();
        List<BasicBuildingInfoProjection> basicBuildingInfoDtos = playerArsenaleRepository.getAllBasicBuildingsInfo(playerId);

        basicBuildingInfoDtos.forEach(building -> {
            AllBasicBuildingsInfoDto b = new AllBasicBuildingsInfoDto();
            b.setId(building.getId());
            b.setNome(building.getNome());
            b.setLivello(building.getLivello());
            b.setUrlImg(building.getUrl());
            basicBuildingsInfo.add(b);
        });
        return new ResponseEntity<>(basicBuildingsInfo, HttpStatus.OK);
    }

    public ResponseEntity<SviluppoArsenaleDettagliDto> getSviluppoArsenaleDettById(Long arsenaleId, String bearerToken) {

        SviluppoArsenaleDettagliDto sviluppoArsenaleDettagliDto = new SviluppoArsenaleDettagliDto();

        int livelloArsenale;
        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();

        Optional<Arsenale> sviluppoArsenaleOptional = arsenaleRepository.getArsenaleById(arsenaleId);
        Arsenale sviluppoArsenale;
        if (sviluppoArsenaleOptional.isPresent()) {
            sviluppoArsenale = sviluppoArsenaleOptional.get();

            Optional<Integer> livelloArsenaleOptional = arsenaleRepository.getArsenaleLvl(playerId, arsenaleId);

            if(livelloArsenaleOptional.isPresent()){
                livelloArsenale = livelloArsenaleOptional.get();
            } else {
                throw new RuntimeException("Livello non presente");
            }
            sviluppoArsenaleDettagliDto.setLivello(livelloArsenale);

            sviluppoArsenaleDettagliDto.setId(sviluppoArsenale.getId());
            sviluppoArsenaleDettagliDto.setNome(sviluppoArsenale.getNome());
            sviluppoArsenaleDettagliDto.setDescrizione(sviluppoArsenale.getDescrizione());
            sviluppoArsenaleDettagliDto.setUrlImmagine(sviluppoArsenale.getUrlImmagine());
            sviluppoArsenaleDettagliDto.setLivelloFabbricaRequisito(sviluppoArsenale.getLivelloFabbricaRequisito());

            sviluppoArsenaleDettagliDto.setAttacco(sviluppoArsenale.getAttacco());
            sviluppoArsenaleDettagliDto.setArmatura(sviluppoArsenale.getArmatura());
            sviluppoArsenaleDettagliDto.setVita(sviluppoArsenale.getVita());
            sviluppoArsenaleDettagliDto.setVelocita(sviluppoArsenale.getVelocita());
            sviluppoArsenaleDettagliDto.setStiva(sviluppoArsenale.getStiva());
            sviluppoArsenaleDettagliDto.setConsumo(sviluppoArsenale.getConsumo());
        } else {
            throw new RuntimeException("Arsenale non trovato");
        }

        sviluppoArsenaleDettagliDto.setChance(String.format("%.0f", alzaLivelloTry.getPercentualeSuccesso(livelloArsenale)));

        Map<String, Double> costi = costiService.getCostiSviluppoArsenale(sviluppoArsenale.getId(), playerId);
        sviluppoArsenaleDettagliDto.setCosti(costi);

        return new ResponseEntity<>(sviluppoArsenaleDettagliDto, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getFabbricaLvl(String bearerToken) {
        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        Optional<Integer> fabbricaLvlOpt = this.struttureRepository.getRequisitoLvl(playerId, "Fabbrica di munizioni");
        int fabbricaLvl;
        if (fabbricaLvlOpt.isPresent()) {
            fabbricaLvl = fabbricaLvlOpt.get();
        } else {
            throw new RuntimeException("Livello fabbrica non trovato");
        }
        return new ResponseEntity<>(fabbricaLvl, HttpStatus.OK);
    }


















}
