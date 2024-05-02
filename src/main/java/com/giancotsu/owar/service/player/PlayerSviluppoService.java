package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.AllBasicBuildingsInfoDto;
import com.giancotsu.owar.dto.SviluppoCompletoDto;
import com.giancotsu.owar.entity.player.PlayerSviluppo;
import com.giancotsu.owar.entity.player.sviluppo.Sviluppo;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.BasicBuildingInfoProjection;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import com.giancotsu.owar.projection.SviluppoProduzioneRisorseProjection;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.PlayerSviluppoRepository;
import com.giancotsu.owar.repository.player.SviluppoRepository;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class PlayerSviluppoService {

    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final SviluppoRepository sviluppoRepository;
    private final UserRepository userRepository;
    private final JWTGenerator jwtGenerator;
    private final AlzaLivelloTry alzaLivelloTry;
    private final CostiService costiService;


    public PlayerSviluppoService(PlayerSviluppoRepository playerSviluppoRepository, SviluppoRepository sviluppoRepository, UserRepository userRepository, JWTGenerator jwtGenerator, AlzaLivelloTry alzaLivelloTry, CostiService costiService) {
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.sviluppoRepository = sviluppoRepository;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        this.alzaLivelloTry = alzaLivelloTry;
        this.costiService = costiService;
        this.creaStrutture();
    }

    private List<Sviluppo> sviluppo = new ArrayList<>();

    private void creaStrutture() {

        // Metallo 1

        String nomeMetallo1 = "MetalForge Industries";
        Optional<Sviluppo> sviluppoDb = sviluppoRepository.findByNome(nomeMetallo1);
        Map<RisorseEnum, Double> costiMetallo = new HashMap<>();

        if (sviluppoDb.isEmpty()) {
            Sviluppo metallo1 = new Sviluppo();
            metallo1.setNome(nomeMetallo1);
            metallo1.setDescrizione("NanoChip Technologies è un'azienda all'avanguardia nel settore della metallurgia e dell'ingegneria dei materiali. Specializzata nella produzione di nanochip metallici ad alte prestazioni, la nostra missione è quella di rivoluzionare il settore tecnologico attraverso l'innovazione e la precisione.");
            metallo1.setUrlImmagine("../../assets/img/buildings/risorse/metal-mine.png");

            costiMetallo.put(RisorseEnum.MICROCHIP, 660.5);
            costiMetallo.put(RisorseEnum.ENERGIA, 200.2);
            costiMetallo.put(RisorseEnum.CIVILI, 350.0);
            costiMetallo.put(RisorseEnum.BITCOIN, 1200.9);

            metallo1.setCostoBase(costiMetallo);
            metallo1.setMoltiplicatoreCosto(1.2051);

            metallo1.setMoltiplicatoreProduzioneRisorse(1.15);
            metallo1.setProduzioneRisorse(Map.of(RisorseEnum.METALLO, 1.3));

            this.sviluppo.add(sviluppoRepository.save(metallo1));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Metallo 2

        String nomeMetallo2 = "SteelWorks Corporation";
        sviluppoDb = sviluppoRepository.findByNome(nomeMetallo2);

        if (sviluppoDb.isEmpty()) {
            Sviluppo metallo2 = new Sviluppo();
            metallo2.setNome(nomeMetallo2);
            metallo2.setDescrizione("Benvenuto alla SteelWorks Corporation, il fulcro industriale del tuo futuro urbano! Situata nel cuore della tua città tecnologicamente avanzata, la SteelWorks Corporation è il centro nevralgico della produzione di metallo di alta qualità per le tue imprese futuristiche. Qui, la tua ingegnosità si fonde con la potenza della tecnologia mentre estrai, raffini e trasformi il metallo per alimentare l'innovazione e il progresso della tua società. Con le sue moderne strutture e l'incessante attività delle tue macchine automatizzate, la SteelWorks Corporation è l'epicentro del tuo impegno nel plasmare un futuro brillante e prospero per la tua civiltà.");
            metallo2.setUrlImmagine("../../assets/img/buildings/risorse/metal-mine2.png");

            costiMetallo.put(RisorseEnum.MICROCHIP, 640.5);
            costiMetallo.put(RisorseEnum.ENERGIA, 210.5);
            costiMetallo.put(RisorseEnum.CIVILI, 330.0);
            costiMetallo.put(RisorseEnum.BITCOIN, 1250.9);

            metallo2.setCostoBase(costiMetallo);
            metallo2.setMoltiplicatoreCosto(1.2054);

            metallo2.setMoltiplicatoreProduzioneRisorse(1.16);
            metallo2.setProduzioneRisorse(Map.of(RisorseEnum.METALLO, 1.3));

            this.sviluppo.add(sviluppoRepository.save(metallo2));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Metallo 3

        String nomeMetallo3 = "AlloyTech Solutions";
        sviluppoDb = sviluppoRepository.findByNome(nomeMetallo3);

        if (sviluppoDb.isEmpty()) {
            Sviluppo metallo3 = new Sviluppo();
            metallo3.setNome(nomeMetallo3);
            metallo3.setDescrizione("Benvenuto ad AlloyTech Solutions, il nucleo tecnologico della tua avventura industriale nel futuro! Situata strategicamente nel cuore della tua metropoli futuristica, AlloyTech Solutions rappresenta l'apice dell'innovazione nella produzione e lavorazione dei metalli più avanzati. Qui, le tue ambizioni prendono vita mentre i tuoi esperti ingegneri e tecnici lavorano instancabilmente per estrarre, raffinare e forgiare leghe metalliche di qualità superiore. Con le sue strutture all'avanguardia e la precisione delle sue macchine automatizzate, AlloyTech Solutions è la fucina del tuo progresso tecnologico, alimentando le tue aspirazioni di creare un futuro in cui la scienza e l'ingegno si fondono per plasmare un mondo migliore.");
            metallo3.setUrlImmagine("../../assets/img/buildings/risorse/metal-mine3.png");

            costiMetallo.put(RisorseEnum.MICROCHIP, 615.5);
            costiMetallo.put(RisorseEnum.ENERGIA, 185.5);
            costiMetallo.put(RisorseEnum.CIVILI, 375.0);
            costiMetallo.put(RisorseEnum.BITCOIN, 1080.9);

            metallo3.setCostoBase(costiMetallo);
            metallo3.setMoltiplicatoreCosto(1.2058);

            metallo3.setMoltiplicatoreProduzioneRisorse(1.15);
            metallo3.setProduzioneRisorse(Map.of(RisorseEnum.METALLO, 1.3));

            this.sviluppo.add(sviluppoRepository.save(metallo3));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // MicroChip 1

        String microchipNome1 = "NanoChip Technologies";
        sviluppoDb = sviluppoRepository.findByNome(microchipNome1);
        Map<RisorseEnum, Double> costiMicrochip = new HashMap<>();

        if (sviluppoDb.isEmpty()) {
            Sviluppo mc1 = new Sviluppo();
            mc1.setNome(microchipNome1);
            mc1.setDescrizione("Benvenuto in NanoChip Technologies, il pioniere dell'innovazione nel campo dei microchip nel mondo del futuro! Situata al vertice della ricerca e dello sviluppo tecnologico, NanoChip Technologies è il cuore pulsante della tua rivoluzione digitale. Qui, un team di menti brillanti lavora incessantemente per progettare e produrre microchip all'avanguardia, capaci di alimentare le tue invenzioni più audaci e di trasformare il modo in cui interagiamo con il mondo digitale. Con la sua dedizione alla qualità e alla precisione, NanoChip Technologies è il motore del tuo progresso tecnologico, spingendo costantemente i confini dell'innovazione per plasmare un futuro in cui la potenza della tecnologia è nelle tue mani.");
            mc1.setUrlImmagine("../../assets/img/buildings/risorse/microchip-factory.png");

            costiMicrochip.put(RisorseEnum.METALLO, 550.3);
            costiMicrochip.put(RisorseEnum.ENERGIA, 190.7);
            costiMicrochip.put(RisorseEnum.CIVILI, 350.1);
            costiMicrochip.put(RisorseEnum.BITCOIN, 1140.5);

            mc1.setCostoBase(costiMicrochip);
            mc1.setMoltiplicatoreCosto(1.2112);

            mc1.setMoltiplicatoreProduzioneRisorse(1.16);
            mc1.setProduzioneRisorse(Map.of(RisorseEnum.MICROCHIP, 1.4));

            this.sviluppo.add(sviluppoRepository.save(mc1));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // MicroChip 2

        String microchipNome2 = "Silicon Forge Solutions";
        sviluppoDb = sviluppoRepository.findByNome(microchipNome2);

        if (sviluppoDb.isEmpty()) {
            Sviluppo mc2 = new Sviluppo();
            mc2.setNome(microchipNome2);
            mc2.setDescrizione("Silicon Forge Solutions: Guidiamo l'innovazione attraverso la forgiatura di soluzioni su misura nel mondo dei semiconduttori. La nostra passione è trasformare le idee in realtà, offrendo tecnologie all'avanguardia per le sfide del futuro.");
            mc2.setUrlImmagine("../../assets/img/buildings/risorse/microchip-factory2.png");

            costiMicrochip.put(RisorseEnum.METALLO, 580.3);
            costiMicrochip.put(RisorseEnum.ENERGIA, 200.7);
            costiMicrochip.put(RisorseEnum.CIVILI, 358.1);
            costiMicrochip.put(RisorseEnum.BITCOIN, 1005.5);

            mc2.setCostoBase(costiMicrochip);
            mc2.setMoltiplicatoreCosto(1.2116);

            mc2.setMoltiplicatoreProduzioneRisorse(1.17);
            mc2.setProduzioneRisorse(Map.of(RisorseEnum.MICROCHIP, 1.4));

            this.sviluppo.add(sviluppoRepository.save(mc2));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // MicroChip 3

        String microchipNome3 = "QuantumCore Semiconductor";
        sviluppoDb = sviluppoRepository.findByNome(microchipNome3);

        if (sviluppoDb.isEmpty()) {
            Sviluppo mc3 = new Sviluppo();
            mc3.setNome(microchipNome3);
            mc3.setDescrizione("QuantumCore Semiconductor è un'azienda all'avanguardia nel settore della produzione di microchip, fondata sull'innovazione e la ricerca scientifica. Guidata da una squadra di esperti nel campo della tecnologia quantistica e della semiconduzione, QuantumCore si impegna a creare microchip di ultima generazione che ridefiniscono i limiti della computazione e dell'elaborazione dati. La nostra missione è quella di sviluppare soluzioni tecnologiche all'avanguardia che consentano ai nostri clienti di spingere i confini dell'innovazione in settori critici come l'intelligenza artificiale, l'Internet delle cose, la sicurezza informatica e molto altro ancora.");
            mc3.setUrlImmagine("../../assets/img/buildings/risorse/microchip-factory3.png");

            costiMicrochip.put(RisorseEnum.METALLO, 535.3);
            costiMicrochip.put(RisorseEnum.ENERGIA, 180.7);
            costiMicrochip.put(RisorseEnum.CIVILI, 340.1);
            costiMicrochip.put(RisorseEnum.BITCOIN, 1450.5);

            mc3.setCostoBase(costiMicrochip);
            mc3.setMoltiplicatoreCosto(1.2118);

            mc3.setMoltiplicatoreProduzioneRisorse(1.2);
            mc3.setProduzioneRisorse(Map.of(RisorseEnum.MICROCHIP, 1.4));

            this.sviluppo.add(sviluppoRepository.save(mc3));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Energia 1

        String energiaNome1 = "Nucleare";
        sviluppoDb = sviluppoRepository.findByNome(energiaNome1);
        Map<RisorseEnum, Double> costiEnergia = new HashMap<>();

        if (sviluppoDb.isEmpty()) {
            Sviluppo energia1 = new Sviluppo();
            energia1.setNome(energiaNome1);
            energia1.setDescrizione("L'energia nucleare è una fonte di energia potente ed efficiente, ottenuta attraverso la fissione nucleare o la fusione nucleare. Offre un approvvigionamento energetico costante e a basso impatto ambientale, ma solleva anche questioni di sicurezza e gestione dei rifiuti radioattivi.");
            energia1.setUrlImmagine("../../assets/img/buildings/risorse/nuclear-power-plant.png");

            costiEnergia.put(RisorseEnum.MICROCHIP, 950.7);
            costiEnergia.put(RisorseEnum.METALLO, 720.1);
            costiEnergia.put(RisorseEnum.CIVILI, 135.2);
            costiEnergia.put(RisorseEnum.BITCOIN, 2650.8);

            energia1.setCostoBase(costiEnergia);
            energia1.setMoltiplicatoreCosto(1.2);

            energia1.setMoltiplicatoreProduzioneRisorse(1.12);
            energia1.setProduzioneRisorse(Map.of(RisorseEnum.ENERGIA, 1.3));

            this.sviluppo.add(sviluppoRepository.save(energia1));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Energia 2

        String energiaNome2 = "Solare";
        sviluppoDb = sviluppoRepository.findByNome(energiaNome2);

        if (sviluppoDb.isEmpty()) {
            Sviluppo energia2 = new Sviluppo();
            energia2.setNome(energiaNome2);
            energia2.setDescrizione("L'energia solare sfrutta la luce del sole per generare elettricità o calore. È una fonte rinnovabile, pulita e abbondante, che contribuisce alla riduzione delle emissioni di gas serra e offre una soluzione sostenibile per le esigenze energetiche globali.");
            energia2.setUrlImmagine("../../assets/img/buildings/risorse/photovoltaic-panels.png");

            costiEnergia.put(RisorseEnum.MICROCHIP, 890.7);
            costiEnergia.put(RisorseEnum.METALLO, 640.1);
            costiEnergia.put(RisorseEnum.CIVILI, 170.2);
            costiEnergia.put(RisorseEnum.BITCOIN, 1850.8);

            energia2.setCostoBase(costiEnergia);
            energia2.setMoltiplicatoreCosto(1.17);

            energia2.setMoltiplicatoreProduzioneRisorse(1.0885);
            energia2.setProduzioneRisorse(Map.of(RisorseEnum.ENERGIA, 1.3));

            this.sviluppo.add(sviluppoRepository.save(energia2));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Energia 3

        String energiaNome3 = "Eolica";
        sviluppoDb = sviluppoRepository.findByNome(energiaNome3);

        if (sviluppoDb.isEmpty()) {
            Sviluppo energia3 = new Sviluppo();
            energia3.setNome(energiaNome3);
            energia3.setDescrizione("L'energia eolica sfrutta la forza del vento per generare elettricità. Utilizzando turbine eoliche, trasforma l'energia cinetica del vento in energia meccanica, che poi viene convertita in elettricità tramite un generatore. È una fonte rinnovabile e pulita, che contribuisce alla riduzione delle emissioni di gas serra e alla diversificazione delle fonti energetiche.");
            energia3.setUrlImmagine("../../assets/img/buildings/risorse/wind-power--power-plant.png");

            costiEnergia.put(RisorseEnum.MICROCHIP, 870.7);
            costiEnergia.put(RisorseEnum.METALLO, 625.1);
            costiEnergia.put(RisorseEnum.CIVILI, 160.2);
            costiEnergia.put(RisorseEnum.BITCOIN, 1950.8);

            energia3.setCostoBase(costiEnergia);
            energia3.setMoltiplicatoreCosto(1.16);

            energia3.setMoltiplicatoreProduzioneRisorse(1.0801);
            energia3.setProduzioneRisorse(Map.of(RisorseEnum.ENERGIA, 1.3));

            this.sviluppo.add(sviluppoRepository.save(energia3));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Civili 1

        String civiliNome1 = "Appartamento";
        sviluppoDb = sviluppoRepository.findByNome(civiliNome1);
        Map<RisorseEnum, Double> costiCivili = new HashMap<>();

        if (sviluppoDb.isEmpty()) {
            Sviluppo civili1 = new Sviluppo();
            civili1.setNome(civiliNome1);
            civili1.setDescrizione("Appartamento: una unità residenziale all'interno di un edificio multifamiliare.");
            civili1.setUrlImmagine("../../assets/img/buildings/risorse/apartment.png");

            costiCivili.put(RisorseEnum.BITCOIN, 1200.5);
            costiCivili.put(RisorseEnum.ACQUA, 50.9);

            civili1.setCostoBase(costiCivili);
            civili1.setMoltiplicatoreCosto(1.15);

            civili1.setMoltiplicatoreProduzioneRisorse(1.08);
            civili1.setProduzioneRisorse(Map.of(RisorseEnum.CIVILI, 1.2));

            this.sviluppo.add(sviluppoRepository.save(civili1));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Civili 2

        String civiliNome2 = "Casa a schiera";
        sviluppoDb = sviluppoRepository.findByNome(civiliNome2);

        if (sviluppoDb.isEmpty()) {
            Sviluppo civili2 = new Sviluppo();
            civili2.setNome(civiliNome2);
            civili2.setDescrizione("Casa a schiera: una serie di case collegate una accanto all'altra, con dividerle un muro.");
            civili2.setUrlImmagine("../../assets/img/buildings/risorse/casa-a-schiera.png");

            costiCivili.put(RisorseEnum.BITCOIN, 1350.5);
            costiCivili.put(RisorseEnum.ACQUA, 45.5);

            civili2.setCostoBase(costiCivili);
            civili2.setMoltiplicatoreCosto(1.168);

            civili2.setMoltiplicatoreProduzioneRisorse(1.06);
            civili2.setProduzioneRisorse(Map.of(RisorseEnum.CIVILI, 1.2));

            this.sviluppo.add(sviluppoRepository.save(civili2));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Civili 3

        String civiliNome3 = "Villa";
        sviluppoDb = sviluppoRepository.findByNome(civiliNome3);

        if (sviluppoDb.isEmpty()) {
            Sviluppo civili3 = new Sviluppo();
            civili3.setNome(civiliNome3);
            civili3.setDescrizione("Villa: una casa di grandi dimensioni, spesso circondata da giardini o terreni.");
            civili3.setUrlImmagine("../../assets/img/buildings/risorse/villa.png");

            costiCivili.put(RisorseEnum.BITCOIN, 1500.5);
            costiCivili.put(RisorseEnum.ACQUA, 40.0);

            civili3.setCostoBase(costiCivili);
            civili3.setMoltiplicatoreCosto(1.185);

            civili3.setMoltiplicatoreProduzioneRisorse(1.04);
            civili3.setProduzioneRisorse(Map.of(RisorseEnum.CIVILI, 1.2));

            this.sviluppo.add(sviluppoRepository.save(civili3));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Bitcoin1

        String bitcoinNome1 = "CryptoVault Bank";
        sviluppoDb = sviluppoRepository.findByNome(bitcoinNome1);
        Map<RisorseEnum, Double> costiBitcoin = new HashMap<>();

        if (sviluppoDb.isEmpty()) {
            Sviluppo bitcoin1 = new Sviluppo();
            bitcoin1.setNome(bitcoinNome1);
            bitcoin1.setDescrizione("CryptoVault Bank è una banca specializzata nella gestione sicura e nell'investimento in Bitcoin e altre criptovalute. Offriamo servizi di deposito e custodia altamente sicuri per i nostri clienti, garantendo la protezione dei loro asset digitali. Con un approccio all'avanguardia e un impegno per l'innovazione nel settore finanziario digitale, ci impegniamo a fornire soluzioni affidabili e convenienti per le esigenze finanziarie nell'ambito delle criptovalute.");
            bitcoin1.setUrlImmagine("../../assets/img/buildings/risorse/bank.png");

            costiBitcoin.put(RisorseEnum.MICROCHIP, 650.7);
            costiBitcoin.put(RisorseEnum.METALLO, 570.3);
            costiBitcoin.put(RisorseEnum.ENERGIA, 390.3);
            costiBitcoin.put(RisorseEnum.CIVILI, 180.9);
            costiBitcoin.put(RisorseEnum.BITCOIN, 500.8);

            bitcoin1.setCostoBase(costiBitcoin);
            bitcoin1.setMoltiplicatoreCosto(1.2);

            bitcoin1.setMoltiplicatoreProduzioneRisorse(1.186);
            bitcoin1.setProduzioneRisorse(Map.of(RisorseEnum.BITCOIN, 1.4));

            this.sviluppo.add(sviluppoRepository.save(bitcoin1));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Bitcoin2

        String bitcoinNome2 = "DigitalCoin Bank";
        sviluppoDb = sviluppoRepository.findByNome(bitcoinNome2);

        if (sviluppoDb.isEmpty()) {
            Sviluppo bitcoin2 = new Sviluppo();
            bitcoin2.setNome(bitcoinNome2);
            bitcoin2.setDescrizione("DigitalCoin Bank è la tua banca di fiducia per l'era delle criptovalute. Offriamo servizi bancari specializzati per Bitcoin e altre valute digitali, garantendo la sicurezza e la facilità d'uso per i nostri clienti. Con soluzioni innovative e un team esperto nel settore delle criptovalute, ci impegniamo a fornire un ambiente sicuro e conveniente per la gestione dei tuoi asset digitali.");
            bitcoin2.setUrlImmagine("../../assets/img/buildings/risorse/bank2.png");

            costiBitcoin.put(RisorseEnum.MICROCHIP, 630.7);
            costiBitcoin.put(RisorseEnum.METALLO, 550.3);
            costiBitcoin.put(RisorseEnum.ENERGIA, 400.3);
            costiBitcoin.put(RisorseEnum.CIVILI, 195.9);
            costiBitcoin.put(RisorseEnum.BITCOIN, 480.8);

            bitcoin2.setCostoBase(costiBitcoin);
            bitcoin2.setMoltiplicatoreCosto(1.22);

            bitcoin2.setMoltiplicatoreProduzioneRisorse(1.190);
            bitcoin2.setProduzioneRisorse(Map.of(RisorseEnum.BITCOIN, 1.4));

            this.sviluppo.add(sviluppoRepository.save(bitcoin2));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Bitcoin3

        String bitcoinNome3 = "Blockchain Trust Group";
        sviluppoDb = sviluppoRepository.findByNome(bitcoinNome3);

        if (sviluppoDb.isEmpty()) {
            Sviluppo bitcoin3 = new Sviluppo();
            bitcoin3.setNome(bitcoinNome3);
            bitcoin3.setDescrizione("Blockchain Trust Group è una banca digitale all'avanguardia specializzata nella gestione e nell'investimento in Bitcoin e altre criptovalute. Offriamo soluzioni sicure e trasparenti per gli investitori che desiderano beneficiare del potenziale della tecnologia blockchain. Con un forte impegno per la fiducia e l'innovazione, ci dedichiamo a fornire servizi bancari di qualità per l'era delle criptovalute.");
            bitcoin3.setUrlImmagine("../../assets/img/buildings/risorse/bank3.png");

            costiBitcoin.put(RisorseEnum.MICROCHIP, 590.0);
            costiBitcoin.put(RisorseEnum.METALLO, 560.3);
            costiBitcoin.put(RisorseEnum.ENERGIA, 440.3);
            costiBitcoin.put(RisorseEnum.CIVILI, 230.9);
            costiBitcoin.put(RisorseEnum.BITCOIN, 485.8);

            bitcoin3.setCostoBase(costiBitcoin);
            bitcoin3.setMoltiplicatoreCosto(1.24);

            bitcoin3.setMoltiplicatoreProduzioneRisorse(1.195);
            bitcoin3.setProduzioneRisorse(Map.of(RisorseEnum.BITCOIN, 1.4));

            this.sviluppo.add(sviluppoRepository.save(bitcoin3));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Acqua1

        String acquaNome1 = "Mare";
        sviluppoDb = sviluppoRepository.findByNome(acquaNome1);
        Map<RisorseEnum, Double> costiAcqua = new HashMap<>();

        if (sviluppoDb.isEmpty()) {
            Sviluppo acqua1 = new Sviluppo();
            acqua1.setNome(acquaNome1);
            acqua1.setDescrizione("Mare: una vasta distesa d'acqua salata che si trova all'interno dei confini continentali, spesso collegata all'oceano.");
            acqua1.setUrlImmagine("../../assets/img/buildings/risorse/sea.png");

            costiAcqua.put(RisorseEnum.MICROCHIP, 480.7);
            costiAcqua.put(RisorseEnum.ENERGIA, 250.3);
            costiAcqua.put(RisorseEnum.BITCOIN, 810.2);

            acqua1.setCostoBase(costiAcqua);
            acqua1.setMoltiplicatoreCosto(1.2);

            acqua1.setMoltiplicatoreProduzioneRisorse(1.0052);
            acqua1.setProduzioneRisorse(Map.of(RisorseEnum.ACQUA, 1.09));

            this.sviluppo.add(sviluppoRepository.save(acqua1));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Acqua2

        String acquaNome2 = "Sorgente";
        sviluppoDb = sviluppoRepository.findByNome(acquaNome2);

        if (sviluppoDb.isEmpty()) {
            Sviluppo acqua2 = new Sviluppo();
            acqua2.setNome(acquaNome2);
            acqua2.setDescrizione("Sorgente: un punto dove l'acqua sgorga naturalmente dal terreno, solitamente fresca e pulita.");
            acqua2.setUrlImmagine("../../assets/img/buildings/risorse/water-source.png");

            costiAcqua.put(RisorseEnum.MICROCHIP, 460.7);
            costiAcqua.put(RisorseEnum.ENERGIA, 210.3);
            costiAcqua.put(RisorseEnum.BITCOIN, 750.2);

            acqua2.setCostoBase(costiAcqua);
            acqua2.setMoltiplicatoreCosto(1.19);

            acqua2.setMoltiplicatoreProduzioneRisorse(1.0035);
            acqua2.setProduzioneRisorse(Map.of(RisorseEnum.ACQUA, 1.09));

            this.sviluppo.add(sviluppoRepository.save(acqua2));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // Acqua3

        String acquaNome3 = "Fiume";
        sviluppoDb = sviluppoRepository.findByNome(acquaNome3);

        if (sviluppoDb.isEmpty()) {
            Sviluppo acqua3 = new Sviluppo();
            acqua3.setNome(acquaNome3);
            acqua3.setDescrizione("Fiume: un flusso naturale d'acqua che scorre da una sorgente verso un'altra massa d'acqua come un lago, un mare o un oceano.");
            acqua3.setUrlImmagine("../../assets/img/buildings/risorse/river.png");

            costiAcqua.put(RisorseEnum.MICROCHIP, 470.2);
            costiAcqua.put(RisorseEnum.ENERGIA, 230.3);
            costiAcqua.put(RisorseEnum.BITCOIN, 780.2);

            acqua3.setCostoBase(costiAcqua);
            acqua3.setMoltiplicatoreCosto(1.195);

            acqua3.setMoltiplicatoreProduzioneRisorse(1.0036);
            acqua3.setProduzioneRisorse(Map.of(RisorseEnum.ACQUA, 1.09));

            this.sviluppo.add(sviluppoRepository.save(acqua3));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }
    }

    public void salvaSviluppo(UserEntity user) {

        for (Sviluppo s : this.sviluppo) {
            PlayerSviluppo playerSviluppo = new PlayerSviluppo();
            playerSviluppo.setPlayer(user.getPlayer());
            playerSviluppo.setSviluppo(s);
            playerSviluppoRepository.save(playerSviluppo);
        }

    }

    public ResponseEntity<SviluppoCompletoDto> getSviluppoDettById(Long sviluppoId, String bearerToken) {

        SviluppoCompletoDto sviluppoCompletoDto = new SviluppoCompletoDto();


        Optional<Sviluppo> sviluppoOptional = sviluppoRepository.findById(sviluppoId);
        if (sviluppoOptional.isPresent()) {
            Sviluppo sviluppo = sviluppoOptional.get();
            sviluppoCompletoDto.setId(sviluppo.getId());
            sviluppoCompletoDto.setNome(sviluppo.getNome());
            sviluppoCompletoDto.setDescrizione(sviluppo.getDescrizione());
            sviluppoCompletoDto.setUrlImmagine(sviluppo.getUrlImmagine());
        } else {
            throw new RuntimeException("Struttura non trovata");
        }

        Long playerId = getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        List<SviluppoCostiProjection> costiProjection = playerSviluppoRepository.getCostiSviluppo(playerId, sviluppoId);
        sviluppoCompletoDto.setLivello(costiProjection.stream().findFirst().get().getLivello());

        Map<String, Double> costi = new HashMap<>();
        costiProjection.forEach(p -> {
            costi.put(p.getRisorsa(), p.getCosto() * (Math.pow(p.getMoltiplicatore(), p.getLivello() - 1.0)));
        });
        sviluppoCompletoDto.setCosti(costi);

        List<SviluppoProduzioneRisorseProjection> produzioneProjection = playerSviluppoRepository.getProduzioneRisorseSviluppo(playerId, sviluppoId);
        Map<String, Double> produzioneAttuale = new HashMap<>();
        Map<String, Double> produzioneProssimoLivello = new HashMap<>();
        produzioneProjection.forEach(p -> {
            long livello = sviluppoCompletoDto.getLivello();
            produzioneAttuale.put(p.getRisorsa(), p.getProduzione() * livello * (Math.pow(p.getMoltiplicatore(), livello)));
            produzioneProssimoLivello.put(p.getRisorsa(), p.getProduzione() * (livello + 1) * (Math.pow(p.getMoltiplicatore(), (livello + 1))));
        });
        sviluppoCompletoDto.setProduzioneAttuale(produzioneAttuale);
        sviluppoCompletoDto.setProduzioneProssimoLivello(produzioneProssimoLivello);


        return new ResponseEntity<>(sviluppoCompletoDto, HttpStatus.OK);
    }

    public ResponseEntity<SviluppoCompletoDto> getSviluppoDettByName(String nomeStruttura, String bearerToken) {

        SviluppoCompletoDto sviluppoCompletoDto = new SviluppoCompletoDto();


        Optional<Sviluppo> sviluppoOptional = sviluppoRepository.findByNome(nomeStruttura);
        Sviluppo sviluppo;
        if (sviluppoOptional.isPresent()) {
            sviluppo = sviluppoOptional.get();
            sviluppoCompletoDto.setId(sviluppo.getId());
            sviluppoCompletoDto.setNome(sviluppo.getNome());
            sviluppoCompletoDto.setDescrizione(sviluppo.getDescrizione());
            sviluppoCompletoDto.setUrlImmagine(sviluppo.getUrlImmagine());
        } else {
            throw new RuntimeException("Struttura non trovata");
        }

        Long playerId = getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        List<SviluppoCostiProjection> costiProjection = playerSviluppoRepository.getCostiSviluppo(playerId, sviluppo.getId());
        int livelloStruttura = costiProjection.stream().findFirst().get().getLivello();
        sviluppoCompletoDto.setLivello(livelloStruttura);
        sviluppoCompletoDto.setChance(String.format("%.0f", alzaLivelloTry.getPercentualeSuccesso(livelloStruttura)));

//        Map<String, Double> costi = new HashMap<>();
//        costiProjection.forEach(p -> {
//            costi.put(p.getRisorsa(), p.getCosto() * (Math.pow(p.getMoltiplicatore(), p.getLivello() - 1.0)));
//        });

        Map<String, Double> costi = costiService.getCostiStruttura(sviluppo.getId(), playerId);
        sviluppoCompletoDto.setCosti(costi);

        List<SviluppoProduzioneRisorseProjection> produzioneProjection = playerSviluppoRepository.getProduzioneRisorseSviluppo(playerId, sviluppo.getId());
        Map<String, Double> produzioneAttuale = new HashMap<>();
        Map<String, Double> produzioneProssimoLivello = new HashMap<>();
        produzioneProjection.forEach(p -> {
            long livello = sviluppoCompletoDto.getLivello();
            produzioneAttuale.put(p.getRisorsa(), p.getProduzione() * livello * (Math.pow(p.getMoltiplicatore(), livello)));
            produzioneProssimoLivello.put(p.getRisorsa(), p.getProduzione() * (livello + 1) * (Math.pow(p.getMoltiplicatore(), (livello + 1))));
        });
        sviluppoCompletoDto.setProduzioneAttuale(produzioneAttuale);
        sviluppoCompletoDto.setProduzioneProssimoLivello(produzioneProssimoLivello);


        return new ResponseEntity<>(sviluppoCompletoDto, HttpStatus.OK);
    }

    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(String bearerToken) {
        List<AllBasicBuildingsInfoDto> basicBuildingsInfo = new ArrayList<>();
        List<BasicBuildingInfoProjection> basicBuildingInfoDtos = playerSviluppoRepository.getAllBasicBuildingsInfo(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());

        basicBuildingInfoDtos.forEach(building -> {
            AllBasicBuildingsInfoDto b = new AllBasicBuildingsInfoDto();
            b.setNome(building.getNome());
            b.setLivello(building.getLivello());
            b.setUrlImg(building.getUrl());
            basicBuildingsInfo.add(b);
        });
        return new ResponseEntity<>(basicBuildingsInfo, HttpStatus.OK);
    }

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




















