package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.SviluppoArsenaleDettagliDto;
import com.giancotsu.owar.dto.SviluppoDifesaDettagliDto;
import com.giancotsu.owar.entity.player.PlayerDifesa;
import com.giancotsu.owar.entity.player.sviluppo.Arsenale;
import com.giancotsu.owar.entity.player.sviluppo.Difesa;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.repository.player.DifesaRepository;
import com.giancotsu.owar.repository.player.PlayerDifesaRepository;
import com.giancotsu.owar.repository.player.StruttureRepository;
import com.giancotsu.owar.utils.JwtUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DifesaService {

    private final DifesaRepository difesaRepository;
    private final PlayerDifesaRepository playerDifesaRepository;
    private final StruttureRepository struttureRepository;
    private final AlzaLivelloTry alzaLivelloTry;
    private final CostiService costiService;
    private final JwtUserUtils jwtUserUtils;

    public DifesaService(DifesaRepository difesaRepository, PlayerDifesaRepository playerDifesaRepository, StruttureRepository struttureRepository, AlzaLivelloTry alzaLivelloTry, CostiService costiService, JwtUserUtils jwtUserUtils) {
        this.difesaRepository = difesaRepository;
        this.playerDifesaRepository = playerDifesaRepository;
        this.struttureRepository = struttureRepository;
        this.alzaLivelloTry = alzaLivelloTry;
        this.costiService = costiService;
        this.jwtUserUtils = jwtUserUtils;

        this.creaDifesa();
    }

    private List<Difesa> difesa = new ArrayList<>();

    private void creaDifesa() {

        // Filo spinato

        String nome1 = "Filo spinato";
        Optional<Difesa> optionalFilo = difesaRepository.findByNome(nome1);
        Map<RisorseEnum, Double> costiFilo = new HashMap<>();
        if(optionalFilo.isEmpty()) {

            String descrizione = String.format("Il %s è una barriera difensiva robusta progettata per rallentare e danneggiare gli intrusi. " +
                    "Efficace nel creare ostacoli, obbliga i nemici a cercare percorsi alternativi, garantendo un prezioso vantaggio strategico.", nome1);

            costiFilo.put(RisorseEnum.MICROCHIP, 150.2);
            costiFilo.put(RisorseEnum.METALLO, 380.2);
            costiFilo.put(RisorseEnum.ENERGIA, 15.3);
            costiFilo.put(RisorseEnum.CIVILI, 10.1);
            costiFilo.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 5;

            long danno = 1;
            long penetrazioneArmatura = 0;
            long armatura = 1;
            long vita = 1;
            long numeroMassimoObbiettivi = 3;
            long numeroMassimoDifesa = 500;

            long dannoNextLvl = 1;
            long penetrazioneArmaturaNextLvl = 0;
            long armaturaNextLvl = 0;
            long vitaNextLvl = 2;
            long numeroMassimoObbiettiviNextLvl = 1;
            long numeroMassimoDifesaNextLvl = 5;

            Difesa dif = new Difesa(null, nome1, descrizione, "../../assets/img/sviluppo-difesa/filo.png", 1.14, costiFilo, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalFilo.get());
        }

        // Mina anti-uomo

        String nome2 = "Mina anti-uomo";
        Optional<Difesa> optionalMina = difesaRepository.findByNome(nome2);
        Map<RisorseEnum, Double> costiMina = new HashMap<>();
        if(optionalMina.isEmpty()) {

            String descrizione = String.format("Il %s è un dispositivo esplosivo nascosto, attivato al passaggio dei nemici. " +
                    "Ideale per difendere perimetri sensibili, infligge danni devastanti e può dissuadere avanzate nemiche, creando zone di esclusione efficaci.", nome2);

            costiMina.put(RisorseEnum.MICROCHIP, 150.2);
            costiMina.put(RisorseEnum.METALLO, 380.2);
            costiMina.put(RisorseEnum.ENERGIA, 15.3);
            costiMina.put(RisorseEnum.CIVILI, 10.1);
            costiMina.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 7;

            long danno = 3;
            long penetrazioneArmatura = 1;
            long armatura = 0;
            long vita = 1;
            long numeroMassimoObbiettivi = 5;
            long numeroMassimoDifesa = 300;

            long dannoNextLvl = 1;
            long penetrazioneArmaturaNextLvl = 0;
            long armaturaNextLvl = 0;
            long vitaNextLvl = 2;
            long numeroMassimoObbiettiviNextLvl = 2;
            long numeroMassimoDifesaNextLvl = 3;

            Difesa dif = new Difesa(null, nome2, descrizione, "../../assets/img/sviluppo-difesa/mina.png", 1.14, costiMina, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalMina.get());
        }

        // Sentinella

        String nome3 = "Sentinella";
        Optional<Difesa> optionalSentinella = difesaRepository.findByNome(nome3);
        Map<RisorseEnum, Double> costiSentinella = new HashMap<>();
        if(optionalSentinella.isEmpty()) {

            String descrizione = String.format("La %s è una truppa d'élite altamente addestrata, specializzata nella sorveglianza e nella difesa di punti strategici. " +
                    "Dotato di armamento avanzato e tecnologia di rilevamento, garantisce una presenza vigilante e pronta a rispondere a qualsiasi minaccia.", nome3);

            costiSentinella.put(RisorseEnum.MICROCHIP, 150.2);
            costiSentinella.put(RisorseEnum.METALLO, 380.2);
            costiSentinella.put(RisorseEnum.ENERGIA, 15.3);
            costiSentinella.put(RisorseEnum.CIVILI, 10.1);
            costiSentinella.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 10;

            long danno = 10;
            long penetrazioneArmatura = 3;
            long armatura = 2;
            long vita = 6;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoDifesa = 500;

            long dannoNextLvl = 3;
            long penetrazioneArmaturaNextLvl = 1;
            long armaturaNextLvl = 0;
            long vitaNextLvl = 1;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoDifesaNextLvl = 10;

            Difesa dif = new Difesa(null, nome3, descrizione, "../../assets/img/sviluppo-difesa/sentinella.png", 1.14, costiSentinella, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalSentinella.get());
        }

        // Torretta leggera

        String nome4 = "Torretta leggera";
        Optional<Difesa> optionalTorretta = difesaRepository.findByNome(nome4);
        Map<RisorseEnum, Double> costiTorretta = new HashMap<>();
        if(optionalTorretta.isEmpty()) {

            String descrizione = String.format("La %s è una difesa automatizzata dotata di una mitragliatrice ad alta velocità. " +
                    "Viene posizionata sule mura difensive ed è ottima per neutralizzare i soldati in prima linea.", nome4);

            costiTorretta.put(RisorseEnum.MICROCHIP, 150.2);
            costiTorretta.put(RisorseEnum.METALLO, 380.2);
            costiTorretta.put(RisorseEnum.ENERGIA, 15.3);
            costiTorretta.put(RisorseEnum.CIVILI, 10.1);
            costiTorretta.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 15;

            long danno = 7;
            long penetrazioneArmatura = 2;
            long armatura = 1;
            long vita = 5;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoDifesa = 400;

            long dannoNextLvl = 3;
            long penetrazioneArmaturaNextLvl = 1;
            long armaturaNextLvl = 0;
            long vitaNextLvl = 1;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoDifesaNextLvl = 4;

            Difesa dif = new Difesa(null, nome4, descrizione, "../../assets/img/sviluppo-difesa/light-turret.png", 1.14, costiTorretta, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalTorretta.get());
        }

        // Torretta pesante

        String nome5 = "Torretta pesante";
        Optional<Difesa> optionalTorrettaPesante = difesaRepository.findByNome(nome5);
        Map<RisorseEnum, Double> costiTorrettaPesante = new HashMap<>();
        if(optionalTorrettaPesante.isEmpty()) {

            String descrizione = String.format("La %s è una difesa molto simile alla sorella minore %s ma molto più potente. " +
                    "Grazie ai suoi nuovi proiettili 10cm più lunghi, questa torretta è in grado di penetrare meglio l'armatura dei soldati nemici.", nome5, nome4);

            costiTorrettaPesante.put(RisorseEnum.MICROCHIP, 150.2);
            costiTorrettaPesante.put(RisorseEnum.METALLO, 380.2);
            costiTorrettaPesante.put(RisorseEnum.ENERGIA, 15.3);
            costiTorrettaPesante.put(RisorseEnum.CIVILI, 10.1);
            costiTorrettaPesante.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 19;

            long danno = 10;
            long penetrazioneArmatura = 5;
            long armatura = 2;
            long vita = 6;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoDifesa = 400;

            long dannoNextLvl = 3;
            long penetrazioneArmaturaNextLvl = 1;
            long armaturaNextLvl = 0;
            long vitaNextLvl = 1;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoDifesaNextLvl = 4;

            Difesa dif = new Difesa(null, nome5, descrizione, "../../assets/img/sviluppo-difesa/heavy-turret.png", 1.14, costiTorrettaPesante, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalTorrettaPesante.get());
        }

        // Torretta di precisione

        String nome6 = "Torretta di precisione";
        Optional<Difesa> optionalTorrettaPrecisione = difesaRepository.findByNome(nome6);
        Map<RisorseEnum, Double> costiTorrettaPrecisione = new HashMap<>();
        if(optionalTorrettaPrecisione.isEmpty()) {

            String descrizione = String.format("La %s è una difesa progettata esclusivamente per ripulire i cieli. " +
                    "Dotata di una super potenza di fuoco, una lunga canna e un avanzato computer interno capace di effettuare calcoli ultra complessi, può colpire con precisione millimetrica obiettivi molto lontani.", nome6);

            costiTorrettaPrecisione.put(RisorseEnum.MICROCHIP, 150.2);
            costiTorrettaPrecisione.put(RisorseEnum.METALLO, 380.2);
            costiTorrettaPrecisione.put(RisorseEnum.ENERGIA, 15.3);
            costiTorrettaPrecisione.put(RisorseEnum.CIVILI, 10.1);
            costiTorrettaPrecisione.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 27;

            long danno = 25;
            long penetrazioneArmatura = 30;
            long armatura = 5;
            long vita = 10;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoDifesa = 400;

            long dannoNextLvl = 5;
            long penetrazioneArmaturaNextLvl = 3;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 2;
            long numeroMassimoObbiettiviNextLvl = 1;
            long numeroMassimoDifesaNextLvl = 4;

            Difesa dif = new Difesa(null, nome6, descrizione, "../../assets/img/sviluppo-difesa/sniper-turret.png", 1.14, costiTorrettaPrecisione, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalTorrettaPrecisione.get());
        }

        // Sistema anti-missilistico

        String nome7 = "Sistema anti-missilistico";
        Optional<Difesa> optionalAntiMissilistico = difesaRepository.findByNome(nome7);
        Map<RisorseEnum, Double> costiAntiMissilistico = new HashMap<>();
        if(optionalAntiMissilistico.isEmpty()) {

            String descrizione = String.format("Il %s è una difesa avanzata progettata per intercettare e distruggere missili in arrivo. " +
                    "Utilizza radar sofisticati e sensori per rilevare le minacce, e lancia missili intercettori con una precisione straordinaria per neutralizzare gli attacchi prima che raggiungano i loro obiettivi.", nome7);

            costiAntiMissilistico.put(RisorseEnum.MICROCHIP, 150.2);
            costiAntiMissilistico.put(RisorseEnum.METALLO, 380.2);
            costiAntiMissilistico.put(RisorseEnum.ENERGIA, 15.3);
            costiAntiMissilistico.put(RisorseEnum.CIVILI, 10.1);
            costiAntiMissilistico.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 13;

            long danno = 20;
            long penetrazioneArmatura = 5;
            long armatura = 3;
            long vita = 5;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoDifesa = 300;

            long dannoNextLvl = 5;
            long penetrazioneArmaturaNextLvl = 3;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 2;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoDifesaNextLvl = 8;

            Difesa dif = new Difesa(null, nome7, descrizione, "../../assets/img/sviluppo-difesa/anti-missile-system.png", 1.14, costiAntiMissilistico, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalAntiMissilistico.get());
        }

        // Cannone

        String nome8 = "Cannone";
        Optional<Difesa> optionalCannone = difesaRepository.findByNome(nome8);
        Map<RisorseEnum, Double> costiCannone = new HashMap<>();
        if(optionalCannone.isEmpty()) {

            String descrizione = String.format("Il %s è una potente arma da difesa stazionaria, capace di infliggere danni massicci a lunga distanza. " +
                    "Ideale per neutralizzare orde di soldati e veicoli corazzati, rappresenta un pilastro di potenza e deterrenza sul campo di battaglia.", nome8);

            costiCannone.put(RisorseEnum.MICROCHIP, 150.2);
            costiCannone.put(RisorseEnum.METALLO, 380.2);
            costiCannone.put(RisorseEnum.ENERGIA, 15.3);
            costiCannone.put(RisorseEnum.CIVILI, 10.1);
            costiCannone.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 17;

            long danno = 6;
            long penetrazioneArmatura = 2;
            long armatura = 6;
            long vita = 15;
            long numeroMassimoObbiettivi = 3;
            long numeroMassimoDifesa = 400;

            long dannoNextLvl = 2;
            long penetrazioneArmaturaNextLvl = 1;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 3;
            long numeroMassimoObbiettiviNextLvl = 1;
            long numeroMassimoDifesaNextLvl = 9;

            Difesa dif = new Difesa(null, nome8, descrizione, "../../assets/img/sviluppo-difesa/explosive-cannon.png", 1.14, costiCannone, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalCannone.get());
        }

        // Missile a ricerca di calore

        String nome9 = "Missile a ricerca di calore";
        Optional<Difesa> optionalMissileCalore = difesaRepository.findByNome(nome9);
        Map<RisorseEnum, Double> costiMissileCalore = new HashMap<>();
        if(optionalMissileCalore.isEmpty()) {

            String descrizione = String.format("Il %s è una difesa progettata per seguire e colpire bersagli emettenti calore, come motori di veicoli o aerei. " +
                    "Utilizza sensori infrarossi per individuare e tracciare le fonti di calore, garantendo una precisione elevata nell'intercettazione del bersaglio.", nome9);

            costiMissileCalore.put(RisorseEnum.MICROCHIP, 150.2);
            costiMissileCalore.put(RisorseEnum.METALLO, 380.2);
            costiMissileCalore.put(RisorseEnum.ENERGIA, 15.3);
            costiMissileCalore.put(RisorseEnum.CIVILI, 10.1);
            costiMissileCalore.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 33;

            long danno = 50;
            long penetrazioneArmatura = 25;
            long armatura = 10;
            long vita = 30;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoDifesa = 500;

            long dannoNextLvl = 10;
            long penetrazioneArmaturaNextLvl = 3;
            long armaturaNextLvl = 2;
            long vitaNextLvl = 4;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoDifesaNextLvl = 10;

            Difesa dif = new Difesa(null, nome9, descrizione, "../../assets/img/sviluppo-difesa/heat-seeking-missiles.png", 1.14, costiMissileCalore, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalMissileCalore.get());
        }

        // Disturbatore radio

        String nome10 = "Disturbatore radio";
        Optional<Difesa> optionalRadio = difesaRepository.findByNome(nome10);
        Map<RisorseEnum, Double> costiRadio = new HashMap<>();
        if(optionalRadio.isEmpty()) {

            String descrizione = String.format("Il %s è un sistema di difesa progettato esclusivamente per la distruzione dei droni. " +
                    "A differenza dei vecchi modelli, che si limitavano a interrompere il contatto tra i droni e i loro controllori, " +
                    "questo disturbatore è ora in grado di emettere forti onde elettromagnetiche capaci di abbattere i droni.", nome10);

            costiRadio.put(RisorseEnum.MICROCHIP, 150.2);
            costiRadio.put(RisorseEnum.METALLO, 380.2);
            costiRadio.put(RisorseEnum.ENERGIA, 15.3);
            costiRadio.put(RisorseEnum.CIVILI, 10.1);
            costiRadio.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 13;

            long danno = 5;
            long penetrazioneArmatura = 1;
            long armatura = 1;
            long vita = 1;
            long numeroMassimoObbiettivi = 10;
            long numeroMassimoDifesa = 100;

            long dannoNextLvl = 10;
            long penetrazioneArmaturaNextLvl = 3;
            long armaturaNextLvl = 2;
            long vitaNextLvl = 4;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoDifesaNextLvl = 10;

            Difesa dif = new Difesa(null, nome10, descrizione, "../../assets/img/sviluppo-difesa/radio-jammer.png", 1.14, costiRadio, livelloFabbricaRequisito,
                    danno, penetrazioneArmatura, armatura, vita, numeroMassimoObbiettivi, numeroMassimoDifesa,
                    dannoNextLvl, penetrazioneArmaturaNextLvl, armaturaNextLvl, vitaNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoDifesaNextLvl);

            this.difesa.add(difesaRepository.save(dif));
        } else {
            this.difesa.add(optionalRadio.get());
        }



    }

    public void salvaDifesa(UserEntity user) {

        for (Difesa d : this.difesa) {
            PlayerDifesa playerDifesa = new PlayerDifesa();
            playerDifesa.setPlayer(user.getPlayer());
            playerDifesa.setDifesa(d);
            playerDifesaRepository.save(playerDifesa);
        }
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(String bearerToken) {
        return this.getAllBasicBuildingsInfoByPlayerId(jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfoByPlayerId(long playerId) {
        List<AllBasicBuildingsInfoDto> basicBuildingsInfo = new ArrayList<>();
        List<BasicBuildingInfoProjection> basicBuildingInfoDtos = playerDifesaRepository.getAllBasicBuildingsInfo(playerId);

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

    public ResponseEntity<SviluppoDifesaDettagliDto> getSviluppoDifesaDettById(Long difesaId, String bearerToken) {

        SviluppoDifesaDettagliDto sviluppoDifesaDettagliDto = new SviluppoDifesaDettagliDto();

        int livelloDifesa;
        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();

        Optional<Difesa> sviluppoDifesaOptional = difesaRepository.getDifesaById(difesaId);
        Difesa sviluppoDifesa;
        if (sviluppoDifesaOptional.isPresent()) {
            sviluppoDifesa = sviluppoDifesaOptional.get();

            Optional<Integer> livelloDifesaOptional = difesaRepository.getDifesaLvl(playerId, difesaId);

            if(livelloDifesaOptional.isPresent()){
                livelloDifesa = livelloDifesaOptional.get();
            } else {
                throw new RuntimeException("Livello non presente");
            }
            sviluppoDifesaDettagliDto.setLivello(livelloDifesa);

            sviluppoDifesaDettagliDto.setId(sviluppoDifesa.getId());
            sviluppoDifesaDettagliDto.setNome(sviluppoDifesa.getNome());
            sviluppoDifesaDettagliDto.setDescrizione(sviluppoDifesa.getDescrizione());
            sviluppoDifesaDettagliDto.setUrlImmagine(sviluppoDifesa.getUrlImmagine());
            sviluppoDifesaDettagliDto.setLivelloFabbricaRequisito(sviluppoDifesa.getLivelloFabbricaRequisito());

            sviluppoDifesaDettagliDto.setDanno(sviluppoDifesa.getDanno());
            sviluppoDifesaDettagliDto.setPenetrazioneArmatura(sviluppoDifesa.getPenetrazioneArmatura());
            sviluppoDifesaDettagliDto.setArmatura(sviluppoDifesa.getArmatura());
            sviluppoDifesaDettagliDto.setVita(sviluppoDifesa.getVita());
            sviluppoDifesaDettagliDto.setNumeroMassimoObbiettivi(sviluppoDifesa.getNumeroMassimoObbiettivi());
            sviluppoDifesaDettagliDto.setNumeroMassimoDifesa(sviluppoDifesa.getNumeroMassimoDifesa());

            sviluppoDifesaDettagliDto.setDannoNextLvl(sviluppoDifesa.getDannoNextLvl());
            sviluppoDifesaDettagliDto.setPenetrazioneArmaturaNextLvl(sviluppoDifesa.getPenetrazioneArmaturaNextLvl());
            sviluppoDifesaDettagliDto.setArmaturaNextLvl(sviluppoDifesa.getArmaturaNextLvl());
            sviluppoDifesaDettagliDto.setVitaNextLvl(sviluppoDifesa.getVitaNextLvl());
            sviluppoDifesaDettagliDto.setNumeroMassimoObbiettiviNextLvl(sviluppoDifesa.getNumeroMassimoObbiettiviNextLvl());
            sviluppoDifesaDettagliDto.setNumeroMassimoDifesaNextLvl(sviluppoDifesa.getNumeroMassimoDifesaNextLvl());

        } else {
            throw new RuntimeException("Difesa non trovata");
        }

        sviluppoDifesaDettagliDto.setChance(String.format("%.0f", alzaLivelloTry.getPercentualeSuccesso(livelloDifesa)));

        Map<String, Double> costi = costiService.getCostiSviluppoDifesa(sviluppoDifesa.getId(), playerId);
        sviluppoDifesaDettagliDto.setCosti(costi);

        return new ResponseEntity<>(sviluppoDifesaDettagliDto, HttpStatus.OK);
    }


}
