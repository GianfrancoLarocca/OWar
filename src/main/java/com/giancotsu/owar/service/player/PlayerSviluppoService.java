package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.SviluppoCompletoDto;
import com.giancotsu.owar.entity.player.PlayerSviluppo;
import com.giancotsu.owar.entity.player.sviluppo.Sviluppo;
import com.giancotsu.owar.entity.risorse.RisorseEnum;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.PlayerRepository;
import com.giancotsu.owar.repository.player.PlayerSviluppoRepository;
import com.giancotsu.owar.repository.player.SviluppoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerSviluppoService {

    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final SviluppoRepository sviluppoRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    public PlayerSviluppoService(PlayerSviluppoRepository playerSviluppoRepository, SviluppoRepository sviluppoRepository, PlayerRepository playerRepository, UserRepository userRepository) {
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.sviluppoRepository = sviluppoRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.creaStrutture();
    }

    private List<Sviluppo> sviluppo = new ArrayList<>();

    private void creaStrutture() {

        // Metallo 1

        String nomeMetallo1 = "MetalForge Industries";
        Optional<Sviluppo> sviluppoDb = sviluppoRepository.findByNome(nomeMetallo1);

        if (sviluppoDb.isEmpty()) {
            Sviluppo metallo1 = new Sviluppo();
            metallo1.setNome(nomeMetallo1);
            metallo1.setDescrizione("NanoChip Technologies è un'azienda all'avanguardia nel settore della metallurgia e dell'ingegneria dei materiali. Specializzata nella produzione di nanochip metallici ad alte prestazioni, la nostra missione è quella di rivoluzionare il settore tecnologico attraverso l'innovazione e la precisione.");
            metallo1.setUrlImmagine("");

            Map<RisorseEnum, Long> costi = new HashMap<>();
            costi.put(RisorseEnum.MICROCHIP, 3L);
            costi.put(RisorseEnum.ENERGIA, 2L);
            costi.put(RisorseEnum.CIVILI, 1L);
            costi.put(RisorseEnum.BITCOIN, 8L);

            metallo1.setCostoBase(costi);
            metallo1.setMoltiplicatoreCosto(3L);

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
            metallo2.setUrlImmagine("");

            Map<RisorseEnum, Long> costi = new HashMap<>();
            costi.put(RisorseEnum.MICROCHIP, 4L);
            costi.put(RisorseEnum.ENERGIA, 1L);
            costi.put(RisorseEnum.CIVILI, 1L);
            costi.put(RisorseEnum.BITCOIN, 9L);

            metallo2.setCostoBase(costi);
            metallo2.setMoltiplicatoreCosto(4L);

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
            metallo3.setUrlImmagine("");

            Map<RisorseEnum, Long> costi = new HashMap<>();
            costi.put(RisorseEnum.MICROCHIP, 1L);
            costi.put(RisorseEnum.ENERGIA, 3L);
            costi.put(RisorseEnum.CIVILI, 2L);
            costi.put(RisorseEnum.BITCOIN, 6L);

            metallo3.setCostoBase(costi);
            metallo3.setMoltiplicatoreCosto(2L);

            this.sviluppo.add(sviluppoRepository.save(metallo3));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }

        // MicroChip 1

        String microchipNome1 = "NanoChip Technologies";
        sviluppoDb = sviluppoRepository.findByNome(microchipNome1);

        if (sviluppoDb.isEmpty()) {
            Sviluppo mc1 = new Sviluppo();
            mc1.setNome(microchipNome1);
            mc1.setDescrizione("Benvenuto in NanoChip Technologies, il pioniere dell'innovazione nel campo dei microchip nel mondo del futuro! Situata al vertice della ricerca e dello sviluppo tecnologico, NanoChip Technologies è il cuore pulsante della tua rivoluzione digitale. Qui, un team di menti brillanti lavora incessantemente per progettare e produrre microchip all'avanguardia, capaci di alimentare le tue invenzioni più audaci e di trasformare il modo in cui interagiamo con il mondo digitale. Con la sua dedizione alla qualità e alla precisione, NanoChip Technologies è il motore del tuo progresso tecnologico, spingendo costantemente i confini dell'innovazione per plasmare un futuro in cui la potenza della tecnologia è nelle tue mani.");
            mc1.setUrlImmagine("");

            Map<RisorseEnum, Long> costi = new HashMap<>();
            costi.put(RisorseEnum.METALLO, 7L);
            costi.put(RisorseEnum.ENERGIA, 3L);
            costi.put(RisorseEnum.CIVILI, 2L);
            costi.put(RisorseEnum.BITCOIN, 10L);

            mc1.setCostoBase(costi);
            mc1.setMoltiplicatoreCosto(2L);

            this.sviluppo.add(sviluppoRepository.save(mc1));
        } else {
            this.sviluppo.add(sviluppoDb.get());
        }
    }

    public void salvaSviluppo(UserEntity user) {

        PlayerSviluppo playerSviluppo = null;

        for (Sviluppo s : this.sviluppo) {
            playerSviluppo = new PlayerSviluppo();
            playerSviluppo.setPlayer(user.getPlayer());
            playerSviluppo.setSviluppo(s);
            playerSviluppoRepository.save(playerSviluppo);
        }

    }

    public ResponseEntity<SviluppoCompletoDto> getSviluppoDett(Long sviluppoId) {

        SviluppoCompletoDto sviluppoCompletoDto = new SviluppoCompletoDto();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByUsername(auth.getName());
        Long playerId;
        if(user.isPresent()) {

            Optional<Sviluppo> sviluppoOptional = sviluppoRepository.findById(sviluppoId);
            if(sviluppoOptional.isPresent()) {
                Sviluppo sviluppo = sviluppoOptional.get();
                sviluppoCompletoDto.setNome(sviluppo.getNome());
                sviluppoCompletoDto.setDescrizione(sviluppo.getDescrizione());
                sviluppoCompletoDto.setUrlImmagine(sviluppo.getUrlImmagine());
            } else {
                throw new RuntimeException("Struttura non trovata");
            }

            playerId = user.get().getPlayer().getId();
            List<SviluppoCostiProjection> projection = playerSviluppoRepository.getSviluppoDto(playerId, sviluppoId);
            sviluppoCompletoDto.setLivello(projection.stream().findFirst().get().getLivello());

            Map<String, Double> costi = new HashMap<>();
            projection.forEach(p -> {
//                System.err.println(p.getRisorsa() + ": " + p.getCosto() * (Math.pow(p.getMoltiplicatore(), p.getLivello())));

                costi.put(p.getRisorsa(), p.getCosto() * (Math.pow(p.getMoltiplicatore(), p.getLivello())));
            });
            sviluppoCompletoDto.setCosti(costi);
        } else {
            throw new RuntimeException("Player non loggato");
        }


        System.err.println(sviluppoCompletoDto);

        return new ResponseEntity<>(sviluppoCompletoDto, HttpStatus.OK);
    }
}




















