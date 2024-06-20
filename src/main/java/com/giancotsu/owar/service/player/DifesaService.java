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

        // Torrette leggere

        String nome1 = "Torretta leggera";
        Optional<Difesa> optionalTorretta = difesaRepository.findByNome(nome1);
        Map<RisorseEnum, Double> costiTorretta = new HashMap<>();
        if(optionalTorretta.isEmpty()) {
            String descrizione = String.format("Le Torrette Leggere sono difese automatizzate dotate di mitragliatrici ad alta velocità. Sono posizionate lungo le mura difensive e sono ottime per neutralizzare i nemici in prima linea.");
            costiTorretta.put(RisorseEnum.MICROCHIP, 150.2);
            costiTorretta.put(RisorseEnum.METALLO, 380.2);
            costiTorretta.put(RisorseEnum.ENERGIA, 15.3);
            costiTorretta.put(RisorseEnum.CIVILI, 10.1);
            costiTorretta.put(RisorseEnum.BITCOIN, 300.9);
            int livelloFabbricaRequisito = 1;
            long danno = 2;
            long penetrazioneArmatura = 0;
            long armatura = 2;
            long vita = 3;
            Difesa torretta = new Difesa(null, nome1, descrizione, "../../assets/img/sviluppo-difesa/light-turret.png", 1.14, costiTorretta, livelloFabbricaRequisito, danno, penetrazioneArmatura, armatura, vita);

            this.difesa.add(difesaRepository.save(torretta));
        } else {
            this.difesa.add(optionalTorretta.get());
        }

        // Filo spinato

        // mine



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

        } else {
            throw new RuntimeException("Difesa non trovata");
        }

        sviluppoDifesaDettagliDto.setChance(String.format("%.0f", alzaLivelloTry.getPercentualeSuccesso(livelloDifesa)));

        Map<String, Double> costi = costiService.getCostiSviluppoDifesa(sviluppoDifesa.getId(), playerId);
        sviluppoDifesaDettagliDto.setCosti(costi);

        return new ResponseEntity<>(sviluppoDifesaDettagliDto, HttpStatus.OK);
    }


}
