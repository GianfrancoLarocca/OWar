package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.SviluppoTecnologiaDettagliDto;
import com.giancotsu.owar.entity.player.PlayerTecnologia;
import com.giancotsu.owar.entity.player.sviluppo.Tecnologia;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.repository.player.PlayerTecnologiaRepository;
import com.giancotsu.owar.repository.player.StruttureRepository;
import com.giancotsu.owar.repository.player.TecnologiaRepository;
import com.giancotsu.owar.utils.JwtUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TecnologiaService {

    private final TecnologiaRepository tecnologiaRepository;
    private final StruttureRepository struttureRepository;
    private final PlayerTecnologiaRepository playerTecnologiaRepository;
    private final AlzaLivelloTry alzaLivelloTry;
    private final CostiService costiService;
    private final JwtUserUtils jwtUserUtils;
    public TecnologiaService(TecnologiaRepository tecnologiaRepository, StruttureRepository struttureRepository, PlayerTecnologiaRepository playerTecnologiaRepository, AlzaLivelloTry alzaLivelloTry, CostiService costiService, JwtUserUtils jwtUserUtils) {
        this.tecnologiaRepository = tecnologiaRepository;
        this.struttureRepository = struttureRepository;
        this.playerTecnologiaRepository = playerTecnologiaRepository;
        this.alzaLivelloTry = alzaLivelloTry;
        this.costiService = costiService;
        this.jwtUserUtils = jwtUserUtils;

        this.creaTecnologie();
    }

    private List<Tecnologia> tecnologie = new ArrayList<>();

    private void creaTecnologie() {

        // Tecnologia scudo

        String nome1 = "Scudo";
        Optional<Tecnologia> optionalScudo = tecnologiaRepository.findByNome(nome1);
        Map<RisorseEnum, Double> costiScudo = new HashMap<>();
        if(optionalScudo.isEmpty()) {
            double effectValue = 9.0;
            String descrizione = String.format("La tecnologia \"%s\" permette di aumentare la durata dello scudo protettivo sul tuo paese dopo un attacco. A ongi livello, verrà incrementata la durata dello scudo di %.0f minuti", nome1, effectValue);
            costiScudo.put(RisorseEnum.MICROCHIP, 250.5);
            costiScudo.put(RisorseEnum.METALLO, 660.5);
            costiScudo.put(RisorseEnum.ENERGIA, 40.5);
            costiScudo.put(RisorseEnum.CIVILI, 25.1);
            costiScudo.put(RisorseEnum.BITCOIN, 950.5);
            Tecnologia scudo = new Tecnologia(null, nome1, descrizione, "../../assets/img/sviluppo-tech/shield.png", 1.14, costiScudo, effectValue, "minuti", 2);


            this.tecnologie.add(tecnologiaRepository.save(scudo));
        } else {
            this.tecnologie.add(optionalScudo.get());
        }

        // Tecnologia performante

        String nome2 = "Performante";
        Optional<Tecnologia> optionalPerformante = tecnologiaRepository.findByNome(nome2);
        Map<RisorseEnum, Double> costiPerformante = new HashMap<>();
        if(optionalPerformante.isEmpty()) {
            double effectValue = 0.2;
            String descrizione = String.format("La tecnologia \"%s\" permette di innalzare l'efficienza di tutte le strutture che producono risorse, riducendone i costi. A ongi livello, i costi degli edifici delle risorse diminuiscono del %.1f%%", nome2, effectValue);
            costiPerformante.put(RisorseEnum.MICROCHIP, 380.5);
            costiPerformante.put(RisorseEnum.METALLO, 190.5);
            costiPerformante.put(RisorseEnum.ENERGIA, 85.8);
            costiPerformante.put(RisorseEnum.CIVILI, 28.1);
            costiPerformante.put(RisorseEnum.BITCOIN, 1250.9);
            Tecnologia performante = new Tecnologia(null, nome2, descrizione, "../../assets/img/sviluppo-tech/performance.png", 1.14, costiPerformante, effectValue, "%", 4);


            this.tecnologie.add(tecnologiaRepository.save(performante));
        } else {
            this.tecnologie.add(optionalPerformante.get());
        }

        // Tecnologia fortuna

        String nome3 = "Fortuna";
        Optional<Tecnologia> optionalFortuna = tecnologiaRepository.findByNome(nome3);
        Map<RisorseEnum, Double> costiFortuna = new HashMap<>();
        if(optionalFortuna.isEmpty()) {
            double effectValue = 0.1;
            String descrizione = String.format("La tecnologia \"%s\" aumenta le chance di incrementare il livello delle strutture. A ongi livello, le chance di incrementare il livello delle strutture sale del %.1f%%", nome3, effectValue);
            costiFortuna.put(RisorseEnum.MICROCHIP, 180.0);
            costiFortuna.put(RisorseEnum.METALLO, 98.9);
            costiFortuna.put(RisorseEnum.ENERGIA, 150.8);
            costiFortuna.put(RisorseEnum.CIVILI, 15.9);
            costiFortuna.put(RisorseEnum.BITCOIN, 1600.9);
            Tecnologia fortuna = new Tecnologia(null, nome3, descrizione, "../../assets/img/sviluppo-tech/four-leaf.png", 1.14, costiFortuna, effectValue, "%", 15);

            this.tecnologie.add(tecnologiaRepository.save(fortuna));
        } else {
            this.tecnologie.add(optionalFortuna.get());
        }

        // Tecnologia





































    }

    public void salvaTecnologie(UserEntity user) {

        for (Tecnologia t : this.tecnologie) {
            PlayerTecnologia playerTecnologia = new PlayerTecnologia();
            playerTecnologia.setPlayer(user.getPlayer());
            playerTecnologia.setTecnologia(t);
            playerTecnologiaRepository.save(playerTecnologia);

            //System.err.println("sto salvando tecnologia: " + t.getNome() + "al player: " + user.getPlayer().getBasicInformation().getNickname());
        }
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(String bearerToken) {
        return this.getAllBasicBuildingsInfoByPlayerId(jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfoByPlayerId(long playerId) {
        List<AllBasicBuildingsInfoDto> basicBuildingsInfo = new ArrayList<>();
        List<BasicBuildingInfoProjection> basicBuildingInfoDtos = playerTecnologiaRepository.getAllBasicBuildingsInfo(playerId);

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

    public ResponseEntity<SviluppoTecnologiaDettagliDto> getSviluppoStrutturaDettById(Long techId, String bearerToken) {

        SviluppoTecnologiaDettagliDto sviluppoTecnologiaDettagliDto = new SviluppoTecnologiaDettagliDto();

        int livelloTech;
        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();

        Optional<Tecnologia> sviluppoTechOptional = tecnologiaRepository.getTecnologiaById(techId);
        Tecnologia sviluppoTech;
        if (sviluppoTechOptional.isPresent()) {
            sviluppoTech = sviluppoTechOptional.get();

            Optional<Integer> livelloTechOptional = tecnologiaRepository.getStrutturaLvl(playerId, techId);

            if(livelloTechOptional.isPresent()){
                livelloTech = livelloTechOptional.get();
            } else {
                throw new RuntimeException("Livello non presente");
            }
            sviluppoTecnologiaDettagliDto.setLivello(livelloTech);

            sviluppoTecnologiaDettagliDto.setId(sviluppoTech.getId());
            sviluppoTecnologiaDettagliDto.setNome(sviluppoTech.getNome());
            sviluppoTecnologiaDettagliDto.setDescrizione(sviluppoTech.getDescrizione());
            sviluppoTecnologiaDettagliDto.setUrlImmagine(sviluppoTech.getUrlImmagine());
            sviluppoTecnologiaDettagliDto.setEffectValue(sviluppoTech.getEffectValue() * livelloTech);
            sviluppoTecnologiaDettagliDto.setNextEffectValue(sviluppoTech.getEffectValue() * (livelloTech + 1));
            sviluppoTecnologiaDettagliDto.setEffectValueType(sviluppoTech.getEffectValueType());
            sviluppoTecnologiaDettagliDto.setLivelloLaboratorioRequisito(sviluppoTech.getLivelloLaboratorioRequisito());
        } else {
            throw new RuntimeException("Tecnologia non trovata");
        }

        sviluppoTecnologiaDettagliDto.setChance(String.format("%.0f", alzaLivelloTry.getPercentualeSuccesso(livelloTech)));

        Map<String, Double> costi = costiService.getCostiSviluppoTech(sviluppoTech.getId(), playerId);
        sviluppoTecnologiaDettagliDto.setCosti(costi);

        return new ResponseEntity<>(sviluppoTecnologiaDettagliDto, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getLabLvl(String bearerToken) {
        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        Optional<Integer> labLvlOpt = this.struttureRepository.getRequisitoLvl(playerId, "Laboratorio di ricerca");
        int labLvl;
        if (labLvlOpt.isPresent()) {
            labLvl = labLvlOpt.get();
        } else {
            throw new RuntimeException("Livello laboratorio non trovato");
        }
        return new ResponseEntity<>(labLvl, HttpStatus.OK);
    }












}
