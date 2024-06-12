package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.SviluppoStrutturaCompletoDto;
import com.giancotsu.owar.entity.player.PlayerStrutture;
import com.giancotsu.owar.entity.player.sviluppo.Strutture;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.PlayerStruttureRepository;
import com.giancotsu.owar.repository.player.StruttureRepository;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class StruttureService {

    private final StruttureRepository struttureRepository;
    private final PlayerStruttureRepository playerStruttureRepository;
    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final AlzaLivelloTry alzaLivelloTry;
    private final CostiService costiService;


    public StruttureService(StruttureRepository struttureRepository, PlayerStruttureRepository playerStruttureRepository, JWTGenerator jwtGenerator, UserRepository userRepository, AlzaLivelloTry alzaLivelloTry, CostiService costiService) {
        this.struttureRepository = struttureRepository;
        this.playerStruttureRepository = playerStruttureRepository;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
        this.alzaLivelloTry = alzaLivelloTry;
        this.costiService = costiService;

        this.creaStrutture();
    }

    private List<Strutture> strutture = new ArrayList<>();

    private void creaStrutture() {

        // Lab. di ricerca

        String nome1 = "Laboratorio di ricerca";
        Optional<Strutture> optionalLaboratorio = struttureRepository.findByNome(nome1);
        Map<RisorseEnum, Double> costiLaboratorio = new HashMap<>();
        if(optionalLaboratorio.isEmpty()) {
            String descrizione = "Il laboratorio di ricerca permette di scoprire nuove tecnologie o di migliorare quelle già esistenti. A ogni livello, incrementi il numero di scienziati; più sono gli scienziati, maggiore sarà la possibilità di migliorare le tecnologie.";
            costiLaboratorio.put(RisorseEnum.MICROCHIP, 520.5);
            costiLaboratorio.put(RisorseEnum.METALLO, 460.5);
            costiLaboratorio.put(RisorseEnum.ENERGIA, 80.5);
            costiLaboratorio.put(RisorseEnum.CIVILI, 45.1);
            costiLaboratorio.put(RisorseEnum.BITCOIN, 800.5);
            Strutture laboratorio = new Strutture(null, nome1, descrizione, "../../assets/img/sviluppo-strutture/research-lab.png", 1.14, costiLaboratorio, 1.50);

            this.strutture.add(struttureRepository.save(laboratorio));
        } else {
            this.strutture.add(optionalLaboratorio.get());
        }

        // Fabbrica di munizioni

        String nome2 = "Fabbrica di munizioni";
        Optional<Strutture> optionalFabbrica = struttureRepository.findByNome(nome2);
        Map<RisorseEnum, Double> costiFabbrica = new HashMap<>();
        if(optionalFabbrica.isEmpty()) {
            String descrizione = "La fabbrica di munizioni permette di produrre qualsiasi tipo di munizione, dai proiettili per i nostri soldati ai missili in dotazione ai nostri veicoli offensivi o macchinari difensivi. A ogni livello, incrementi la potenza di fuoco delle munizioni.";
            costiFabbrica.put(RisorseEnum.MICROCHIP, 350.5);
            costiFabbrica.put(RisorseEnum.METALLO, 510.5);
            costiFabbrica.put(RisorseEnum.ENERGIA, 120.5);
            costiFabbrica.put(RisorseEnum.CIVILI, 20.5);
            costiFabbrica.put(RisorseEnum.BITCOIN, 340.5);
            Strutture fabbrica = new Strutture(null, nome2, descrizione, "../../assets/img/sviluppo-strutture/munitions-factory.png", 1.14, costiFabbrica, 1.75);

            this.strutture.add(struttureRepository.save(fabbrica));
        } else {
            this.strutture.add(optionalFabbrica.get());
        }

        // Campo di addestramento

        String nome3 = "Campo di addestramento";
        Optional<Strutture> optionalAddestramento = struttureRepository.findByNome(nome3);
        Map<RisorseEnum, Double> costiAddestramento = new HashMap<>();
        if(optionalAddestramento.isEmpty()) {
            String descrizione = "Il campo di addestramento è il luogo dove i nostri soldati migliorano le loro abilità combattive. Più alto è il livello del campo di addestramento, meno tempo impiegherai per addestrare nuove truppe.";
            costiAddestramento.put(RisorseEnum.MICROCHIP, 280.5);
            costiAddestramento.put(RisorseEnum.METALLO, 230.5);
            costiAddestramento.put(RisorseEnum.ENERGIA, 10.5);
            costiAddestramento.put(RisorseEnum.CIVILI, 175.9);
            costiAddestramento.put(RisorseEnum.BITCOIN, 570.5);
            Strutture addestramento = new Strutture(null, nome3, descrizione, "../../assets/img/sviluppo-strutture/training-camp.png", 1.14, costiAddestramento, 1.90);

            this.strutture.add(struttureRepository.save(addestramento));
        } else {
            this.strutture.add(optionalAddestramento.get());
        }

        // Caveau

        String nome4 = "Caveau";
        Optional<Strutture> optionalCaveau = struttureRepository.findByNome(nome4);
        Map<RisorseEnum, Double> costiCaveau = new HashMap<>();
        if(optionalCaveau.isEmpty()) {
            String descrizione = "Il caveau serve per difendere le risorse (Microchip e Metallo) dagli attacchi avversari. A ogni livello, incrementi lo spazio per conservare le risorse.";
            costiCaveau.put(RisorseEnum.MICROCHIP, 99.9);
            costiCaveau.put(RisorseEnum.METALLO, 580.9);
            costiCaveau.put(RisorseEnum.ENERGIA, 20.9);
            costiCaveau.put(RisorseEnum.CIVILI, 80.9);
            costiCaveau.put(RisorseEnum.BITCOIN, 1030.0);
            Strutture caveau = new Strutture(null, nome4, descrizione, "../../assets/img/sviluppo-strutture/vaults.png", 1.14, costiCaveau, 2.00);

            this.strutture.add(struttureRepository.save(caveau));
        } else {
            this.strutture.add(optionalCaveau.get());
        }

        // Bunker

        String nome5 = "Bunker";
        Optional<Strutture> optionalBunker = struttureRepository.findByNome(nome5);
        Map<RisorseEnum, Double> costiBunker = new HashMap<>();
        if(optionalBunker.isEmpty()) {
            String descrizione = "Il bunker serve per difendere i civili e le scorte di acqua dagli attacchi avversari. A ogni livello, incrementi lo spazio del bunker.";
            costiBunker.put(RisorseEnum.MICROCHIP, 120.4);
            costiBunker.put(RisorseEnum.METALLO, 510.3);
            costiBunker.put(RisorseEnum.ENERGIA, 25.7);
            costiBunker.put(RisorseEnum.CIVILI, 85.2);
            costiBunker.put(RisorseEnum.BITCOIN, 920.3);
            Strutture bunker = new Strutture(null, nome5, descrizione, "../../assets/img/sviluppo-strutture/bunker.png", 1.14, costiBunker, 2.00);

            this.strutture.add(struttureRepository.save(bunker));
        } else {
            this.strutture.add(optionalBunker.get());
        }

        // Info

        String nome6 = "CyberGuardians Inc.";
        Optional<Strutture> optionalInfo = struttureRepository.findByNome(nome6);
        Map<RisorseEnum, Double> costiInfo = new HashMap<>();
        if(optionalInfo.isEmpty()) {
            String descrizione = "CyberGuardians Inc. è un'azienda di sicurezza informatica con il compito di proteggere i bitcoin dagli attacchi nemici. A ogni livello, la quantità di bitcoin protetta aumenta.";
            costiInfo.put(RisorseEnum.MICROCHIP, 325.1);
            costiInfo.put(RisorseEnum.METALLO, 30.3);
            costiInfo.put(RisorseEnum.ENERGIA, 95.4);
            costiInfo.put(RisorseEnum.CIVILI, 120.1);
            costiInfo.put(RisorseEnum.BITCOIN, 769.5);
            Strutture info = new Strutture(null, nome6, descrizione, "../../assets/img/sviluppo-strutture/cybersecurity.png", 1.14, costiInfo, 2.00);

            this.strutture.add(struttureRepository.save(info));
        } else {
            this.strutture.add(optionalInfo.get());
        }

        // Info

        String nome7 = "Deposito bellico";
        Optional<Strutture> optionalDeposito = struttureRepository.findByNome(nome7);
        Map<RisorseEnum, Double> costiDeposito = new HashMap<>();
        if(optionalDeposito.isEmpty()) {
            String descrizione = "Il deposito bellico è una struttura adibita a deposito di munizioni, esplosivi, veicoli e altre risorse utilizzate ai fini della guerra. A ogni livello, incrementi lo spazio del deposito.";
            costiDeposito.put(RisorseEnum.MICROCHIP, 20.9);
            costiDeposito.put(RisorseEnum.METALLO, 180.9);
            costiDeposito.put(RisorseEnum.ENERGIA, 20.9);
            costiDeposito.put(RisorseEnum.CIVILI, 45.9);
            costiDeposito.put(RisorseEnum.BITCOIN, 712.9);
            Strutture deposito = new Strutture(null, nome7, descrizione, "../../assets/img/sviluppo-strutture/war-depot.png", 1.14, costiDeposito, 5.30);

            this.strutture.add(struttureRepository.save(deposito));
        } else {
            this.strutture.add(optionalDeposito.get());
        }

        // Infermeria

        String nome8 = "Infermeria";
        Optional<Strutture> optionalInfermeria = struttureRepository.findByNome(nome8);
        Map<RisorseEnum, Double> costiInfermeria = new HashMap<>();
        if(optionalInfermeria.isEmpty()) {
            String descrizione = "L'infermeria è la struttura destinata alla cura dei soldati feriti in guerra. Più alto è il livello, più veloci saranno le cure.";
            costiInfermeria.put(RisorseEnum.MICROCHIP, 20.9);
            costiInfermeria.put(RisorseEnum.METALLO, 180.1);
            costiInfermeria.put(RisorseEnum.ENERGIA, 20.9);
            costiInfermeria.put(RisorseEnum.CIVILI, 45.2);
            costiInfermeria.put(RisorseEnum.BITCOIN, 712.4);
            Strutture infermeria = new Strutture(null, nome8, descrizione, "../../assets/img/sviluppo-strutture/infirmary.png", 1.14, costiInfermeria, 3.5);

            this.strutture.add(struttureRepository.save(infermeria));
        } else {
            this.strutture.add(optionalInfermeria.get());
        }

        String nome9 = "Cimitero di guerra";
        Optional<Strutture> optionalCimitero = struttureRepository.findByNome(nome9);
        Map<RisorseEnum, Double> costiCimitero = new HashMap<>();
        if(optionalCimitero.isEmpty()) {
            String descrizione = "Il cimitero di guerra è la struttura destinata alla riparazione dei veicoli andati distutti dopo una battaglia. Più alto è il livello, più veloci saranno le riparazioni.";
            costiCimitero.put(RisorseEnum.MICROCHIP, 40.3);
            costiCimitero.put(RisorseEnum.METALLO, 200.2);
            costiCimitero.put(RisorseEnum.ENERGIA, 25.7);
            costiCimitero.put(RisorseEnum.CIVILI, 15.5);
            costiCimitero.put(RisorseEnum.BITCOIN, 650.1);
            Strutture cimitero = new Strutture(null, nome9, descrizione, "../../assets/img/sviluppo-strutture/graveyard.png", 1.14, costiCimitero, 4.2);

            this.strutture.add(struttureRepository.save(cimitero));
        } else {
            this.strutture.add(optionalCimitero.get());
        }


    }

    public void salvaStrutture(UserEntity user) {

        for (Strutture s : this.strutture) {
            PlayerStrutture playerStrutture = new PlayerStrutture();
            playerStrutture.setPlayer(user.getPlayer());
            playerStrutture.setStrutture(s);
            playerStruttureRepository.save(playerStrutture);
        }
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(String bearerToken) {
        return this.getAllBasicBuildingsInfoByPlayerId(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfoByPlayerId(long playerId) {
        List<AllBasicBuildingsInfoDto> basicBuildingsInfo = new ArrayList<>();
        List<BasicBuildingInfoProjection> basicBuildingInfoDtos = playerStruttureRepository.getAllBasicBuildingsInfo(playerId);

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

    public ResponseEntity<SviluppoStrutturaCompletoDto> getSviluppoStrutturaDettById(Long sviluppoStrutturaId, String bearerToken) {

        SviluppoStrutturaCompletoDto sviluppoStrutturaCompletoDto = new SviluppoStrutturaCompletoDto();

        int livelloStruttura;
        Long playerId = getUserFromAuthorizationToken(bearerToken).getPlayer().getId();

        Optional<Strutture> sviluppoStruttureOptional = struttureRepository.getStrutturaById(sviluppoStrutturaId);
        Strutture sviluppoStrutture;
        if (sviluppoStruttureOptional.isPresent()) {
            sviluppoStrutture = sviluppoStruttureOptional.get();

            Optional<Integer> livelloStrutturaOptional = struttureRepository.getStrutturaLvl(playerId, sviluppoStrutturaId);

            if(livelloStrutturaOptional.isPresent()){
                livelloStruttura = livelloStrutturaOptional.get();
            } else {
                throw new RuntimeException("Livello non presente");
            }
            sviluppoStrutturaCompletoDto.setLivello(livelloStruttura);

            sviluppoStrutturaCompletoDto.setId(sviluppoStrutture.getId());
            sviluppoStrutturaCompletoDto.setNome(sviluppoStrutture.getNome());
            sviluppoStrutturaCompletoDto.setDescrizione(sviluppoStrutture.getDescrizione());
            sviluppoStrutturaCompletoDto.setUrlImmagine(sviluppoStrutture.getUrlImmagine());
            sviluppoStrutturaCompletoDto.setEffectValue(sviluppoStrutture.getEffectValue() * livelloStruttura);
            sviluppoStrutturaCompletoDto.setNextEffectValue(sviluppoStrutture.getEffectValue() * (livelloStruttura + 1));
        } else {
            throw new RuntimeException("Struttura non trovata");
        }



        sviluppoStrutturaCompletoDto.setChance(String.format("%.0f", alzaLivelloTry.getPercentualeSuccesso(livelloStruttura)));


        Map<String, Double> costi = costiService.getCostiSviluppoStruttura(sviluppoStrutture.getId(), playerId);
        sviluppoStrutturaCompletoDto.setCosti(costi);

        return new ResponseEntity<>(sviluppoStrutturaCompletoDto, HttpStatus.OK);
    }

    //UTILITIES
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






























































