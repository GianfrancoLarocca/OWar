package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.SviluppoArsenaleDettagliDto;
import com.giancotsu.owar.entity.player.PlayerArsenale;
import com.giancotsu.owar.entity.player.PlayerDifesa;
import com.giancotsu.owar.entity.player.sviluppo.Arsenale;
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

            String descrizione = String.format("La %s è una pistola semi-automatica di ultima generazione, progettata per offrire prestazioni eccellenti in situazioni di alta pressione. " +
                    "Costruita con materiali leggeri e resistenti, la P070 è la scelta ideale per chi cerca affidabilità e precisione sul campo.", nome1);

            costiPistola.put(RisorseEnum.MICROCHIP, 150.2);
            costiPistola.put(RisorseEnum.METALLO, 380.2);
            costiPistola.put(RisorseEnum.ENERGIA, 15.3);
            costiPistola.put(RisorseEnum.CIVILI, 10.1);
            costiPistola.put(RisorseEnum.BITCOIN, 300.9);

            int livelloFabbricaRequisito = 2;

            long attacco = 5;
            long armatura = 2;
            long vita = 3;
            long velocita = 15;
            long stiva = 0;
            long consumo = 0;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 1000;

            long attaccoNextLvl = 1;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 1;
            long velocitaNextLvl = 2;
            long stivaNextLvl = 0;
            long consumoNextLvl = 0;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 50;

            Arsenale p070 = new Arsenale(null, nome1, descrizione, "../../assets/img/sviluppo-arsenale/pistol.png", 1.14, costiPistola, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(p070));
        } else {
            this.arsenale.add(optionalPistola.get());
        }

        // TT9000

        String nome2 = "TT9000";
        Optional<Arsenale> optionalTT9000 = arsenaleRepository.findByNome(nome2);
        Map<RisorseEnum, Double> costiTT9000 = new HashMap<>();
        if(optionalTT9000.isEmpty()) {

            String descrizione = String.format("La %s è una mitraglietta compatta e potente, progettata per offrire un'elevata cadenza di fuoco e precisione in ogni situazione. " +
                    "Costruita con materiali all'avanguardia, è la scelta ideale per operazioni tattiche e combattimenti ravvicinati.", nome2);

            costiTT9000.put(RisorseEnum.MICROCHIP, 170.1);
            costiTT9000.put(RisorseEnum.METALLO, 400.1);
            costiTT9000.put(RisorseEnum.ENERGIA, 15.5);
            costiTT9000.put(RisorseEnum.CIVILI, 11.9);
            costiTT9000.put(RisorseEnum.BITCOIN, 415.9);

            int livelloFabbricaRequisito = 4;

            long attacco = 15;
            long armatura = 3;
            long vita = 5;
            long velocita = 13;
            long stiva = 0;
            long consumo = 0;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 1000;

            long attaccoNextLvl = 2;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 1;
            long velocitaNextLvl = 1;
            long stivaNextLvl = 0;
            long consumoNextLvl = 0;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 50;

            Arsenale tt9000 = new Arsenale(null, nome2, descrizione, "../../assets/img/sviluppo-arsenale/machine-gun.png", 1.14, costiTT9000, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(tt9000));
        } else {
            this.arsenale.add(optionalTT9000.get());
        }

        // S99K

        String nome3 = "S99K";
        Optional<Arsenale> optionalS99K = arsenaleRepository.findByNome(nome3);
        Map<RisorseEnum, Double> costiS99K = new HashMap<>();
        if(optionalS99K.isEmpty()) {

            String descrizione = String.format("L' %s è un fucile a pompa robusto e affidabile, progettato per offrire un'elevata potenza di fuoco e versatilità in ogni situazione. " +
                    "Ideale per operazioni tattiche, difesa personale e uso in ambienti ostili.", nome3);

            costiS99K.put(RisorseEnum.MICROCHIP, 140.9);
            costiS99K.put(RisorseEnum.METALLO, 420.8);
            costiS99K.put(RisorseEnum.ENERGIA, 17.7);
            costiS99K.put(RisorseEnum.CIVILI, 13.7);
            costiS99K.put(RisorseEnum.BITCOIN, 450.2);

            int livelloFabbricaRequisito = 6;

            long attacco = 18;
            long armatura = 5;
            long vita = 8;
            long velocita = 10;
            long stiva = 0;
            long consumo = 0;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 1000;

            long attaccoNextLvl = 2;
            long armaturaNextLvl = 2;
            long vitaNextLvl = 1;
            long velocitaNextLvl = 1;
            long stivaNextLvl = 0;
            long consumoNextLvl = 0;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 50;

            Arsenale s99k = new Arsenale(null, nome3, descrizione, "../../assets/img/sviluppo-arsenale/shotgun.png", 1.14, costiS99K, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(s99k));
        } else {
            this.arsenale.add(optionalS99K.get());
        }

        // BR88EVO

        String nome4 = "BR88EVO";
        Optional<Arsenale> optionalBR88EVO = arsenaleRepository.findByNome(nome4);
        Map<RisorseEnum, Double> costiBR88EVO = new HashMap<>();
        if(optionalBR88EVO.isEmpty()) {

            String descrizione = String.format("Il %s è un fucile d'assalto avanzato, progettato per garantire precisione, potenza e affidabilità in ogni missione. " +
                    "Ideale per operazioni militari e tattiche, combina tecnologia moderna con una costruzione robusta.", nome4);

            costiBR88EVO.put(RisorseEnum.MICROCHIP, 199.9);
            costiBR88EVO.put(RisorseEnum.METALLO, 450.8);
            costiBR88EVO.put(RisorseEnum.ENERGIA, 20.8);
            costiBR88EVO.put(RisorseEnum.CIVILI, 18.9);
            costiBR88EVO.put(RisorseEnum.BITCOIN, 550.9);

            int livelloFabbricaRequisito = 10;

            long attacco = 40;
            long armatura = 10;
            long vita = 12;
            long velocita = 8;
            long stiva = 0;
            long consumo = 0;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 1000;

            long attaccoNextLvl = 3;
            long armaturaNextLvl = 2;
            long vitaNextLvl = 2;
            long velocitaNextLvl = 1;
            long stivaNextLvl = 0;
            long consumoNextLvl = 0;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 50;

            Arsenale br88evo = new Arsenale(null, nome4, descrizione, "../../assets/img/sviluppo-arsenale/assoult-riffle.png", 1.14, costiBR88EVO, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(br88evo));
        } else {
            this.arsenale.add(optionalBR88EVO.get());
        }

        // AKK164

        String nome5 = "AKK164";
        Optional<Arsenale> optionalAKK164 = arsenaleRepository.findByNome(nome5);
        Map<RisorseEnum, Double> costiAKK164 = new HashMap<>();
        if(optionalAKK164.isEmpty()) {

            String descrizione = String.format("L' %s è un fucile d'assalto affidabile e potente, progettato per eccellere in condizioni di combattimento difficili. " +
                    "Questo fucile combina la tradizionale robustezza con le innovazioni moderne per garantire precisione e versatilità sul campo di battaglia.", nome5);

            costiAKK164.put(RisorseEnum.MICROCHIP, 230.5);
            costiAKK164.put(RisorseEnum.METALLO, 455.2);
            costiAKK164.put(RisorseEnum.ENERGIA, 22.2);
            costiAKK164.put(RisorseEnum.CIVILI, 20.1);
            costiAKK164.put(RisorseEnum.BITCOIN, 700.1);

            int livelloFabbricaRequisito = 14;

            long attacco = 55;
            long armatura = 11;
            long vita = 15;
            long velocita = 6;
            long stiva = 0;
            long consumo = 0;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 1000;

            long attaccoNextLvl = 5;
            long armaturaNextLvl = 3;
            long vitaNextLvl = 2;
            long velocitaNextLvl = 2;
            long stivaNextLvl = 0;
            long consumoNextLvl = 0;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 50;

            Arsenale akk164 = new Arsenale(null, nome5, descrizione, "../../assets/img/sviluppo-arsenale/assoult-riffle-ak.png", 1.14, costiAKK164, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(akk164));
        } else {
            this.arsenale.add(optionalAKK164.get());
        }

        // SP3R

        String nome6 = "SP3R";
        Optional<Arsenale> optionalSP3R = arsenaleRepository.findByNome(nome6);
        Map<RisorseEnum, Double> costiSP3R = new HashMap<>();
        if(optionalSP3R.isEmpty()) {

            String descrizione = String.format("L' %s è un fucile da cecchino, progettato per colpire bersagli a lunga distanza con letale accuratezza. " +
                    "Realizzato con materiali di alta qualità, offre stabilità e affidabilità in ogni tiro.", nome6);

            costiSP3R.put(RisorseEnum.MICROCHIP, 250.9);
            costiSP3R.put(RisorseEnum.METALLO, 600.9);
            costiSP3R.put(RisorseEnum.ENERGIA, 25.9);
            costiSP3R.put(RisorseEnum.CIVILI, 25.1);
            costiSP3R.put(RisorseEnum.BITCOIN, 950.9);

            int livelloFabbricaRequisito = 17;

            long attacco = 80;
            long armatura = 7;
            long vita = 10;
            long velocita = 3;
            long stiva = 0;
            long consumo = 0;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 1000;

            long attaccoNextLvl = 8;
            long armaturaNextLvl = 2;
            long vitaNextLvl = 3;
            long velocitaNextLvl = 1;
            long stivaNextLvl = 0;
            long consumoNextLvl = 0;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 50;

            Arsenale sp3r = new Arsenale(null, nome6, descrizione, "../../assets/img/sviluppo-arsenale/sniper.png", 1.14, costiSP3R, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(sp3r));
        } else {
            this.arsenale.add(optionalSP3R.get());
        }

        // Granata

        String nome7 = "Granata";
        Optional<Arsenale> optionalGranata = arsenaleRepository.findByNome(nome7);
        Map<RisorseEnum, Double> costiGranata = new HashMap<>();
        if(optionalGranata.isEmpty()) {

            String descrizione = String.format("La %s è un esplosivo di piccole dimensioni ma di gran impatto, molto utile per indebolire le difese leggere avversarie.", nome7);

            costiGranata.put(RisorseEnum.MICROCHIP, 100.2);
            costiGranata.put(RisorseEnum.METALLO, 415.2);
            costiGranata.put(RisorseEnum.ENERGIA, 20.3);
            costiGranata.put(RisorseEnum.CIVILI, 6.1);
            costiGranata.put(RisorseEnum.BITCOIN, 280.9);

            int livelloFabbricaRequisito = 3;

            long attacco = 20;
            long armatura = 1;
            long vita = 2;
            long velocita = 15;
            long stiva = 0;
            long consumo = 0;
            long numeroMassimoObbiettivi = 3;
            long numeroMassimoArma = 500;

            long attaccoNextLvl = 3;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 1;
            long velocitaNextLvl = 2;
            long stivaNextLvl = 0;
            long consumoNextLvl = 0;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 25;

            Arsenale granata = new Arsenale(null, nome7, descrizione, "../../assets/img/sviluppo-arsenale/granata.png", 1.14, costiGranata, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(granata));
        } else {
            this.arsenale.add(optionalGranata.get());
        }

        // Drone esplosivo

        String nome8 = "Drone esplosivo";
        Optional<Arsenale> optionalDrone = arsenaleRepository.findByNome(nome8);
        Map<RisorseEnum, Double> costiDrone = new HashMap<>();
        if(optionalDrone.isEmpty()) {

            String descrizione = String.format("Il %s è un drone di medie dimensioni, che viene telecomandato e fatto esplodere in prossimità dell'obbiettivo. Molto utile contro le medio-piccole difese.", nome8);

            costiDrone.put(RisorseEnum.MICROCHIP, 300.9);
            costiDrone.put(RisorseEnum.METALLO, 130.9);
            costiDrone.put(RisorseEnum.ENERGIA, 30.9);
            costiDrone.put(RisorseEnum.CIVILI, 10.1);
            costiDrone.put(RisorseEnum.BITCOIN, 310.9);

            int livelloFabbricaRequisito = 5;

            long attacco = 35;
            long armatura = 3;
            long vita = 4;
            long velocita = 30;
            long stiva = 0;
            long consumo = 5;
            long numeroMassimoObbiettivi = 5;
            long numeroMassimoArma = 500;

            long attaccoNextLvl = 5;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 1;
            long velocitaNextLvl = 3;
            long stivaNextLvl = 0;
            long consumoNextLvl = 1;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 25;

            Arsenale drone = new Arsenale(null, nome8, descrizione, "../../assets/img/sviluppo-arsenale/explosive-drone.png", 1.14, costiDrone, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(drone));
        } else {
            this.arsenale.add(optionalDrone.get());
        }

        // Sistema missilistico

        String nome9 = "Sistema missilistico";
        Optional<Arsenale> optionalMissili = arsenaleRepository.findByNome(nome9);
        Map<RisorseEnum, Double> costiMissili = new HashMap<>();
        if(optionalMissili.isEmpty()) {

            String descrizione = String.format("Il %s è in grado di lanciare missili ad elevata velocità e ad alto potenziale esplosivo contro bersagli a lunga gittata. Utilissimo contro difese o veicoli pesanti.", nome9);

            costiMissili.put(RisorseEnum.MICROCHIP, 450.9);
            costiMissili.put(RisorseEnum.METALLO, 300.9);
            costiMissili.put(RisorseEnum.ENERGIA, 50.9);
            costiMissili.put(RisorseEnum.CIVILI, 15.1);
            costiMissili.put(RisorseEnum.BITCOIN, 500.9);

            int livelloFabbricaRequisito = 7;

            long attacco = 60;
            long armatura = 2;
            long vita = 10;
            long velocita = 100;
            long stiva = 0;
            long consumo = 20;
            long numeroMassimoObbiettivi = 10;
            long numeroMassimoArma = 300;

            long attaccoNextLvl = 10;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 4;
            long velocitaNextLvl = 10;
            long stivaNextLvl = 0;
            long consumoNextLvl = 2;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 10;

            Arsenale missili = new Arsenale(null, nome9, descrizione, "../../assets/img/sviluppo-arsenale/missile-system.png", 1.14, costiMissili, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(missili));
        } else {
            this.arsenale.add(optionalMissili.get());
        }

        // Aer M-00DY

        String nome10 = "Aer M-00DY";
        Optional<Arsenale> optionalMoody = arsenaleRepository.findByNome(nome10);
        Map<RisorseEnum, Double> costiMoody = new HashMap<>();
        if(optionalMoody.isEmpty()) {

            String descrizione = String.format("L'%s è un elicottero da guerra ormai deprecato, fù progettato per operazioni di combattimento in prima linea. " +
                    "Con la sua tecnologia obsoleta e la incapacità di eseguire missioni complesse, l'%s è un potente strumento per far da scudo ad altri elicotteri più moderni e potenti.", nome10, nome10);

            costiMoody.put(RisorseEnum.MICROCHIP, 300.9);
            costiMoody.put(RisorseEnum.METALLO, 800.9);
            costiMoody.put(RisorseEnum.ENERGIA, 50.9);
            costiMoody.put(RisorseEnum.CIVILI, 30.1);
            costiMoody.put(RisorseEnum.BITCOIN, 1400.9);

            int livelloFabbricaRequisito = 22;

            long attacco = 170;
            long armatura = 40;
            long vita = 25;
            long velocita = 30;
            long stiva = 50;
            long consumo = 100;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 800;

            long attaccoNextLvl = 10;
            long armaturaNextLvl = 5;
            long vitaNextLvl = 3;
            long velocitaNextLvl = 4;
            long stivaNextLvl = 5;
            long consumoNextLvl = 4;
            long numeroMassimoObbiettiviNextLvl = 1;
            long numeroMassimoArmaNextLvl = 30;

            Arsenale moody = new Arsenale(null, nome10, descrizione, "../../assets/img/sviluppo-arsenale/moody.png", 1.14, costiMoody, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(moody));
        } else {
            this.arsenale.add(optionalMoody.get());
        }

        // Aer D-00DY

        String nome11 = "Aer D-00DY";
        Optional<Arsenale> optionalDoody = arsenaleRepository.findByNome(nome11);
        Map<RisorseEnum, Double> costiDoody = new HashMap<>();
        if(optionalDoody.isEmpty()) {

            String descrizione = String.format("L'%s è un elicottero rimodernizzato che prende ispirazione dal vecchio elicottero degli anni '80 \"Aer D-00DY 1980\". " +
                    "Questo nuovo elicottero è dotato di tecnologie moderne e, grazie alle sue piccole dimensioni, è estremamente rapido. L'%s si è affermato sul mercato come il migliore per qualità/prezzo.", nome11, nome11);

            costiDoody.put(RisorseEnum.MICROCHIP, 320.9);
            costiDoody.put(RisorseEnum.METALLO, 850.9);
            costiDoody.put(RisorseEnum.ENERGIA, 65.9);
            costiDoody.put(RisorseEnum.CIVILI, 35.1);
            costiDoody.put(RisorseEnum.BITCOIN, 1750.9);

            int livelloFabbricaRequisito = 26;

            long attacco = 190;
            long armatura = 55;
            long vita = 40;
            long velocita = 45;
            long stiva = 80;
            long consumo = 130;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 650;

            long attaccoNextLvl = 7;
            long armaturaNextLvl = 5;
            long vitaNextLvl = 4;
            long velocitaNextLvl = 3;
            long stivaNextLvl = 10;
            long consumoNextLvl = 6;
            long numeroMassimoObbiettiviNextLvl = 2;
            long numeroMassimoArmaNextLvl = 20;

            Arsenale doody = new Arsenale(null, nome11, descrizione, "../../assets/img/sviluppo-arsenale/doody.png", 1.14, costiDoody, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(doody));
        } else {
            this.arsenale.add(optionalDoody.get());
        }

        // Aer ZANQ-92

        String nome13 = "Aer ZANQ v92";
        Optional<Arsenale> optionalZanq = arsenaleRepository.findByNome(nome13);
        Map<RisorseEnum, Double> costiZanq = new HashMap<>();
        if(optionalZanq.isEmpty()) {

            String descrizione = String.format("L'%s è conosciuto da tutto il mondo come l'elicottero più potente di tutti. " +
                    "Questo elicottero porta con se un motore super performante, delle pale enormi, e un arsenale distruttivo. L'unico lato negativo è il suo costo spropositato.", nome13);

            costiZanq.put(RisorseEnum.MICROCHIP, 500.9);
            costiZanq.put(RisorseEnum.METALLO, 1000.9);
            costiZanq.put(RisorseEnum.ENERGIA, 100.9);
            costiZanq.put(RisorseEnum.CIVILI, 50.1);
            costiZanq.put(RisorseEnum.BITCOIN, 2390.9);

            int livelloFabbricaRequisito = 34;

            long attacco = 250;
            long armatura = 82;
            long vita = 64;
            long velocita = 70;
            long stiva = 150;
            long consumo = 200;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 500;

            long attaccoNextLvl = 10;
            long armaturaNextLvl = 8;
            long vitaNextLvl = 6;
            long velocitaNextLvl = 4;
            long stivaNextLvl = 15;
            long consumoNextLvl = 8;
            long numeroMassimoObbiettiviNextLvl = 3;
            long numeroMassimoArmaNextLvl = 10;

            Arsenale zanq = new Arsenale(null, nome13, descrizione, "../../assets/img/sviluppo-arsenale/zanq.png", 1.14, costiZanq, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(zanq));
        } else {
            this.arsenale.add(optionalZanq.get());
        }

        // BOMB-DODO

        String nome14 = "BOMB-DODO";
        Optional<Arsenale> optionalDodo = arsenaleRepository.findByNome(nome14);
        Map<RisorseEnum, Double> costiDodo = new HashMap<>();
        if(optionalDodo.isEmpty()) {

            String descrizione = String.format("Il %s è un potentissimo bombardiere capace di annientare un intero campo di battaglia. " +
                    "Il suo grande svantaggio è la lentezza, che lo rende un facile bersaglio.", nome14);

            costiDodo.put(RisorseEnum.MICROCHIP, 650.9);
            costiDodo.put(RisorseEnum.METALLO, 1200.9);
            costiDodo.put(RisorseEnum.ENERGIA, 128.9);
            costiDodo.put(RisorseEnum.CIVILI, 55.1);
            costiDodo.put(RisorseEnum.BITCOIN, 3099.9);

            int livelloFabbricaRequisito = 40;

            long attacco = 500;
            long armatura = 100;
            long vita = 80;
            long velocita = 35;
            long stiva = 200;
            long consumo = 450;
            long numeroMassimoObbiettivi = 10;
            long numeroMassimoArma = 100;

            long attaccoNextLvl = 15;
            long armaturaNextLvl = 10;
            long vitaNextLvl = 10;
            long velocitaNextLvl = 1;
            long stivaNextLvl = 20;
            long consumoNextLvl = 15;
            long numeroMassimoObbiettiviNextLvl = 5;
            long numeroMassimoArmaNextLvl = 5;

            Arsenale dodo = new Arsenale(null, nome14, descrizione, "../../assets/img/sviluppo-arsenale/dodo.png", 1.14, costiDodo, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(dodo));
        } else {
            this.arsenale.add(optionalDodo.get());
        }

        // FF IC-10

        String nome15 = "FF IC-10";
        Optional<Arsenale> optionalIcio = arsenaleRepository.findByNome(nome15);
        Map<RisorseEnum, Double> costiIcio = new HashMap<>();
        if(optionalIcio.isEmpty()) {

            String descrizione = String.format("L'%s è un aereo da caccia che, nonostante sia datato, rimane comunque affidabile e svolge bene il proprio lavoro. " +
                    "Fu progettato per eccellere nei combattimenti aerei e nelle missioni di superiorità aerea. Il suo grande pregio è il costo decisamente inferiore rispetto al suo successore.", nome15);

            costiIcio.put(RisorseEnum.MICROCHIP, 800.9);
            costiIcio.put(RisorseEnum.METALLO, 1450.9);
            costiIcio.put(RisorseEnum.ENERGIA, 150.9);
            costiIcio.put(RisorseEnum.CIVILI, 70.1);
            costiIcio.put(RisorseEnum.BITCOIN, 5800.9);

            int livelloFabbricaRequisito = 43;

            long attacco = 300;
            long armatura = 65;
            long vita = 50;
            long velocita = 120;
            long stiva = 10;
            long consumo = 720;
            long numeroMassimoObbiettivi = 5;
            long numeroMassimoArma = 80;

            long attaccoNextLvl = 10;
            long armaturaNextLvl = 5;
            long vitaNextLvl = 7;
            long velocitaNextLvl = 15;
            long stivaNextLvl = 1;
            long consumoNextLvl = 20;
            long numeroMassimoObbiettiviNextLvl = 2;
            long numeroMassimoArmaNextLvl = 7;

            Arsenale icio = new Arsenale(null, nome15, descrizione, "../../assets/img/sviluppo-arsenale/icio.png", 1.14, costiIcio, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(icio));
        } else {
            this.arsenale.add(optionalIcio.get());
        }

        // JJ M4-NU

        String nome16 = "JJ M4-NU";
        Optional<Arsenale> optionalManu = arsenaleRepository.findByNome(nome16);
        Map<RisorseEnum, Double> costiManu = new HashMap<>();
        if(optionalManu.isEmpty()) {

            String descrizione = String.format("Il %s è un aereo da caccia di ultima generazione, progettato per dominare i cieli con una potenza e una precisione ineguagliabili. " +
                    "Equipaggiato con tecnologia stealth e armamenti avanzati, il JJ M4-NU è un formidabile avversario in qualsiasi teatro di guerra.", nome16);

            costiManu.put(RisorseEnum.MICROCHIP, 1450.9);
            costiManu.put(RisorseEnum.METALLO, 2300.9);
            costiManu.put(RisorseEnum.ENERGIA, 243.9);
            costiManu.put(RisorseEnum.CIVILI, 120.1);
            costiManu.put(RisorseEnum.BITCOIN, 9500.9);

            int livelloFabbricaRequisito = 47;

            long attacco = 435;
            long armatura = 80;
            long vita = 70;
            long velocita = 200;
            long stiva = 15;
            long consumo = 1000;
            long numeroMassimoObbiettivi = 10;
            long numeroMassimoArma = 50;

            long attaccoNextLvl = 20;
            long armaturaNextLvl = 10;
            long vitaNextLvl = 10;
            long velocitaNextLvl = 30;
            long stivaNextLvl = 2;
            long consumoNextLvl = 30;
            long numeroMassimoObbiettiviNextLvl = 4;
            long numeroMassimoArmaNextLvl = 5;

            Arsenale manu = new Arsenale(null, nome16, descrizione, "../../assets/img/sviluppo-arsenale/manu.png", 1.14, costiManu, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(manu));
        } else {
            this.arsenale.add(optionalManu.get());
        }

        // Virus informatico

        String nome17 = "Virus informatico";
        Optional<Arsenale> optionalVirus = arsenaleRepository.findByNome(nome17);
        Map<RisorseEnum, Double> costiVirus = new HashMap<>();
        if(optionalVirus.isEmpty()) {

            String descrizione = String.format("Il %s è un software malevolo capace di prelevare ingenti somme di bitcoin dalle banche nemiche.", nome17);

            costiVirus.put(RisorseEnum.MICROCHIP, 300.9);
            costiVirus.put(RisorseEnum.METALLO, 150.9);
            costiVirus.put(RisorseEnum.ENERGIA, 35.9);
            costiVirus.put(RisorseEnum.CIVILI, 12.1);
            costiVirus.put(RisorseEnum.BITCOIN, 340.9);

            int livelloFabbricaRequisito = 8;

            long attacco = 0;
            long armatura = 2;
            long vita = 2;
            long velocita = 20;
            long stiva = 0;
            long consumo = 0;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 10;

            long attaccoNextLvl = 0;
            long armaturaNextLvl = 2;
            long vitaNextLvl = 2;
            long velocitaNextLvl = 3;
            long stivaNextLvl = 0;
            long consumoNextLvl = 0;
            long numeroMassimoObbiettiviNextLvl = 1;
            long numeroMassimoArmaNextLvl = 2;

            Arsenale virus = new Arsenale(null, nome17, descrizione, "../../assets/img/sviluppo-arsenale/virus.png", 1.14, costiVirus, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(virus));
        } else {
            this.arsenale.add(optionalVirus.get());
        }

        // Cargo

        String nome18 = "Cargo";
        Optional<Arsenale> optionalCargo = arsenaleRepository.findByNome(nome18);
        Map<RisorseEnum, Double> costiCargo = new HashMap<>();
        if(optionalCargo.isEmpty()) {

            String descrizione = String.format("Il %s è un mezzo blindato dotato di una grande stiva, utilizzato in guerra per raccogliere tutto ciò che può essere saccheggiato.", nome18);

            costiCargo.put(RisorseEnum.MICROCHIP, 40.9);
            costiCargo.put(RisorseEnum.METALLO, 560.9);
            costiCargo.put(RisorseEnum.ENERGIA, 40.9);
            costiCargo.put(RisorseEnum.CIVILI, 35.1);
            costiCargo.put(RisorseEnum.BITCOIN, 450.9);

            int livelloFabbricaRequisito = 9;

            long attacco = 0;
            long armatura = 50;
            long vita = 45;
            long velocita = 35;
            long stiva = 500;
            long consumo = 80;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 1000;

            long attaccoNextLvl = 0;
            long armaturaNextLvl = 5;
            long vitaNextLvl = 4;
            long velocitaNextLvl = 3;
            long stivaNextLvl = 25;
            long consumoNextLvl = 10;
            long numeroMassimoObbiettiviNextLvl = 1;
            long numeroMassimoArmaNextLvl = 100;

            Arsenale cargo = new Arsenale(null, nome18, descrizione, "../../assets/img/sviluppo-arsenale/cargo.png", 1.14, costiCargo, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(cargo));
        } else {
            this.arsenale.add(optionalCargo.get());
        }

        // Drone spia

        String nome19 = "Drone spia";
        Optional<Arsenale> optionalDroneSpia = arsenaleRepository.findByNome(nome19);
        Map<RisorseEnum, Double> costiDroneSpia = new HashMap<>();
        if(optionalDroneSpia.isEmpty()) {

            String descrizione = String.format("Il %s è un drone super leggero in grado di volare ad alte quote e ad alte velocità. " +
                    "È munito di telecamere che lo rendono perfetto per il suo compito principale: carpire informazioni nemiche.", nome19);

            costiDroneSpia.put(RisorseEnum.MICROCHIP, 300.9);
            costiDroneSpia.put(RisorseEnum.METALLO, 185.9);
            costiDroneSpia.put(RisorseEnum.ENERGIA, 28.9);
            costiDroneSpia.put(RisorseEnum.CIVILI, 10.1);
            costiDroneSpia.put(RisorseEnum.BITCOIN, 372.9);

            int livelloFabbricaRequisito = 12;

            long attacco = 0;
            long armatura = 2;
            long vita = 2;
            long velocita = 70;
            long stiva = 0;
            long consumo = 2;
            long numeroMassimoObbiettivi = 1;
            long numeroMassimoArma = 300;

            long attaccoNextLvl = 0;
            long armaturaNextLvl = 1;
            long vitaNextLvl = 1;
            long velocitaNextLvl = 7;
            long stivaNextLvl = 0;
            long consumoNextLvl = 2;
            long numeroMassimoObbiettiviNextLvl = 0;
            long numeroMassimoArmaNextLvl = 5;

            Arsenale droneSpia = new Arsenale(null, nome19, descrizione, "../../assets/img/sviluppo-arsenale/spia.png", 1.14, costiDroneSpia, livelloFabbricaRequisito,
                    attacco, armatura, vita, velocita, stiva, consumo, numeroMassimoObbiettivi, numeroMassimoArma,
                    attaccoNextLvl, armaturaNextLvl, vitaNextLvl, velocitaNextLvl, stivaNextLvl, consumoNextLvl, numeroMassimoObbiettiviNextLvl, numeroMassimoArmaNextLvl);

            this.arsenale.add(arsenaleRepository.save(droneSpia));
        } else {
            this.arsenale.add(optionalDroneSpia.get());
        }
    }

    public void salvaArsenale(UserEntity user) {

        for (Arsenale t : this.arsenale) {
            PlayerArsenale playerArsenale = new PlayerArsenale();
            playerArsenale.setPlayer(user.getPlayer());
            playerArsenale.setArsenale(t);

            playerArsenale.setLivelloFabbricaRequisito(t.getLivelloFabbricaRequisito());

            playerArsenale.setAttacco(t.getAttacco());
            playerArsenale.setArmatura(t.getArmatura());
            playerArsenale.setVita(t.getVita());
            playerArsenale.setVelocita(t.getVelocita());
            playerArsenale.setStiva(t.getStiva());
            playerArsenale.setConsumo(t.getConsumo());
            playerArsenale.setNumeroMassimoObbiettivi(t.getNumeroMassimoObbiettivi());
            playerArsenale.setNumeroMassimoArma(t.getNumeroMassimoArma());

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
            b.setRequisito(building.getRequisito());
            basicBuildingsInfo.add(b);
        });
        return new ResponseEntity<>(basicBuildingsInfo, HttpStatus.OK);
    }

    public ResponseEntity<SviluppoArsenaleDettagliDto> getSviluppoArsenaleDettById(Long arsenaleId, String bearerToken) {

        SviluppoArsenaleDettagliDto sviluppoArsenaleDettagliDto = new SviluppoArsenaleDettagliDto();

        int livelloArsenale;
        Long playerId = jwtUserUtils.getUserFromAuthorizationToken(bearerToken).getPlayer().getId();

        Optional<PlayerArsenale> pa = playerArsenaleRepository.findPlayerArsenaleByPlayerIdAndArsenaleId(playerId, arsenaleId);
        PlayerArsenale playerArsenale;
        if (pa.isPresent()) {
            playerArsenale  = pa.get();
        } else {
            throw new RuntimeException("Player non presente");
        }

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

            sviluppoArsenaleDettagliDto.setAttacco(playerArsenale.getAttacco());
            sviluppoArsenaleDettagliDto.setArmatura(playerArsenale.getArmatura());
            sviluppoArsenaleDettagliDto.setVita(playerArsenale.getVita());
            sviluppoArsenaleDettagliDto.setVelocita(playerArsenale.getVelocita());
            sviluppoArsenaleDettagliDto.setStiva(playerArsenale.getStiva());
            sviluppoArsenaleDettagliDto.setConsumo(playerArsenale.getConsumo());
            sviluppoArsenaleDettagliDto.setNumeroMassimoObbiettivi(playerArsenale.getNumeroMassimoObbiettivi());
            sviluppoArsenaleDettagliDto.setNumeroMassimoArma(playerArsenale.getNumeroMassimoArma());

            sviluppoArsenaleDettagliDto.setAttaccoNextLvl(sviluppoArsenale.getAttaccoNextLvl());
            sviluppoArsenaleDettagliDto.setArmaturaNextLvl(sviluppoArsenale.getArmaturaNextLvl());
            sviluppoArsenaleDettagliDto.setVitaNextLvl(sviluppoArsenale.getVitaNextLvl());
            sviluppoArsenaleDettagliDto.setVelocitaNextLvl(sviluppoArsenale.getVelocitaNextLvl());
            sviluppoArsenaleDettagliDto.setStivaNextLvl(sviluppoArsenale.getStivaNextLvl());
            sviluppoArsenaleDettagliDto.setConsumoNextLvl(sviluppoArsenale.getConsumoNextLvl());
            sviluppoArsenaleDettagliDto.setNumeroMassimoObbiettiviNextLvl(sviluppoArsenale.getNumeroMassimoObbiettiviNextLvl());
            sviluppoArsenaleDettagliDto.setNumeroMassimoArmaNextLvl(sviluppoArsenale.getNumeroMassimoArmaNextLvl());

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
