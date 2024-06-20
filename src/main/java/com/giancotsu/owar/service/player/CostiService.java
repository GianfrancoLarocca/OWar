package com.giancotsu.owar.service.player;

import com.giancotsu.owar.entity.player.*;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.*;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CostiService {

    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final PlayerStruttureRepository playerStruttureRepository;
    private final PlayerTecnologiaRepository playerTecnologiaRepository;
    private final PlayerArsenaleRepository playerArsenaleRepository;
    private final PlayerDifesaRepository playerDifesaRepository;
    private final RisorseService risorseService;
    private final UserRepository userRepository;
    private final JWTGenerator jwtGenerator;

    public CostiService(PlayerSviluppoRepository playerSviluppoRepository, PlayerStruttureRepository playerStruttureRepository, PlayerTecnologiaRepository playerTecnologiaRepository, PlayerArsenaleRepository playerArsenaleRepository, PlayerDifesaRepository playerDifesaRepository, RisorseService risorseService, UserRepository userRepository, JWTGenerator jwtGenerator) {
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.playerStruttureRepository = playerStruttureRepository;
        this.playerTecnologiaRepository = playerTecnologiaRepository;
        this.playerArsenaleRepository = playerArsenaleRepository;
        this.playerDifesaRepository = playerDifesaRepository;
        this.risorseService = risorseService;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    private Double getCosto(Double costoBase, Double moltiplicatore, int livello) {
        return costoBase * Math.pow(moltiplicatore, livello - 1.0);
    }


    public Map<String, Double> getCostiStruttura(Long strutturaId, Long playerId) {

        Optional<PlayerSviluppo> ps = playerSviluppoRepository.findByPlayerIdAndSviluppoId(playerId, strutturaId);
        if (ps.isPresent()) {

            List<SviluppoCostiProjection> costiProjection = playerSviluppoRepository.getCostiSviluppo(playerId, strutturaId);
            int livelloStruttura = costiProjection.stream().findFirst().get().getLivello();

            Map<String, Double> costi = new HashMap<>();
            costiProjection.forEach(p -> {
                costi.put(p.getRisorsa(), getCosto(p.getCosto(), p.getMoltiplicatore(), livelloStruttura));
            });

            return costi;
        }

        throw new RuntimeException("Struttura non trovata");
    }

    public Map<String, Double> getCostiSviluppoStruttura(Long strutturaId, Long playerId) {

        Optional<PlayerStrutture> ps = playerStruttureRepository.findByPlayerIdAndSviluppoId(playerId, strutturaId);
        if (ps.isPresent()) {

            List<SviluppoCostiProjection> costiProjection = playerStruttureRepository.getCostiSviluppo(playerId, strutturaId);
            int livelloStruttura = costiProjection.stream().findFirst().get().getLivello();

            Map<String, Double> costi = new HashMap<>();
            costiProjection.forEach(p -> {
                costi.put(p.getRisorsa(), getCosto(p.getCosto(), p.getMoltiplicatore(), livelloStruttura));
            });

            return costi;
        }

        throw new RuntimeException("Struttura non trovata");
    }

    public Map<String, Double> getCostiSviluppoTech(Long techId, Long playerId) {

        Optional<PlayerTecnologia> pt = playerTecnologiaRepository.findByPlayerIdAndSviluppoId(playerId, techId);
        if (pt.isPresent()) {

            List<SviluppoCostiProjection> costiProjection = playerTecnologiaRepository.getCostiSviluppo(playerId, techId);
            int livelloStruttura = costiProjection.stream().findFirst().get().getLivello();

            Map<String, Double> costi = new HashMap<>();
            costiProjection.forEach(p -> {
                costi.put(p.getRisorsa(), getCosto(p.getCosto(), p.getMoltiplicatore(), livelloStruttura));
            });

            return costi;
        }

        throw new RuntimeException("Tecnologia non trovata");
    }

    public Map<String, Double> getCostiSviluppoArsenale(Long arsenaleId, Long playerId) {

        Optional<PlayerArsenale> pa = playerArsenaleRepository.findByPlayerIdAndSviluppoId(playerId, arsenaleId);
        if (pa.isPresent()) {

            List<SviluppoCostiProjection> costiProjection = playerArsenaleRepository.getCostiSviluppo(playerId, arsenaleId);
            int livelloStruttura = costiProjection.stream().findFirst().get().getLivello();

            Map<String, Double> costi = new HashMap<>();
            costiProjection.forEach(p -> {
                costi.put(p.getRisorsa(), getCosto(p.getCosto(), p.getMoltiplicatore(), livelloStruttura));
            });

            return costi;
        }

        throw new RuntimeException("Arsenale non trovato");
    }

    public Map<String, Double> getCostiSviluppoDifesa(Long difesaId, Long playerId) {

        Optional<PlayerDifesa> pd = playerDifesaRepository.findByPlayerIdAndSviluppoId(playerId, difesaId);
        if (pd.isPresent()) {

            List<SviluppoCostiProjection> costiProjection = playerDifesaRepository.getCostiSviluppo(playerId, difesaId);
            int livelloStruttura = costiProjection.stream().findFirst().get().getLivello();

            Map<String, Double> costi = new HashMap<>();
            costiProjection.forEach(p -> {
                costi.put(p.getRisorsa(), getCosto(p.getCosto(), p.getMoltiplicatore(), livelloStruttura));
            });

            return costi;
        }

        throw new RuntimeException("Difesa non trovata");
    }

    public boolean canPay(Long strutturaId, String bearerToken) {

        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);

        Map<String, Double> playerResources = risorseService.getPlayerResources(playerId);
        Map<String, Double> strutturaCosti = getCostiStruttura(strutturaId, playerId);

        for (String risorsa : strutturaCosti.keySet()) {
            if (playerResources.get(risorsa) < strutturaCosti.get(risorsa)) {
                return false;
            }
        }
        return true;
    }

    public boolean canPayStrutture(Long strutturaId, String bearerToken) {

        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);

        Map<String, Double> playerResources = risorseService.getPlayerResources(playerId);
        Map<String, Double> strutturaCosti = getCostiSviluppoStruttura(strutturaId, playerId);

        for (String risorsa : strutturaCosti.keySet()) {
            if (playerResources.get(risorsa) < strutturaCosti.get(risorsa)) {
                return false;
            }
        }
        return true;
    }

    public boolean canPayTech(Long techId, String bearerToken) {

        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);

        Map<String, Double> playerResources = risorseService.getPlayerResources(playerId);
        Map<String, Double> techCosti = getCostiSviluppoTech(techId, playerId);

        for (String risorsa : techCosti.keySet()) {
            if (playerResources.get(risorsa) < techCosti.get(risorsa)) {
                return false;
            }
        }
        return true;
    }

    public boolean canPayNew(String bearerToken, Map<String, Double> costi) {

        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);

        Map<String, Double> playerResources = risorseService.getPlayerResources(playerId);

        for (String risorsa : costi.keySet()) {
            if (playerResources.get(risorsa) < costi.get(risorsa)) {
                return false;
            }
        }
        return true;
    }

    public void pay(Long strutturaId, String bearerToken) {
        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);
        risorseService.payment(getCostiStruttura(strutturaId, playerId), playerId);
    }

    public void paySviluppoStruttura(Long strutturaId, String bearerToken) {
        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);
        risorseService.payment(getCostiSviluppoStruttura(strutturaId, playerId), playerId);
    }

    public void paySviluppoTech(Long strutturaId, String bearerToken) {
        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);
        risorseService.payment(getCostiSviluppoTech(strutturaId, playerId), playerId);
    }

    public void paySviluppoArsenale(Long strutturaId, String bearerToken) {
        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);
        risorseService.payment(getCostiSviluppoArsenale(strutturaId, playerId), playerId);
    }

    public void paySviluppoDifesa(Long strutturaId, String bearerToken) {
        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);
        risorseService.payment(getCostiSviluppoDifesa(strutturaId, playerId), playerId);
    }

    public Double convertCostToExp(Long strutturaId, String bearerToken) {

        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);

        Double exp = 0.0;

        Map<String, Double> strutturaCosti = getCostiStruttura(strutturaId, playerId);

        for (String risorsa : strutturaCosti.keySet()) {
            switch (risorsa) {
                case "BITCOIN":
                    exp += strutturaCosti.get(risorsa) * 0.05;
                    break;
                case "MICROCHIP":
                    exp += strutturaCosti.get(risorsa) * 0.08;
                    break;
                case "METALLO":
                    exp += strutturaCosti.get(risorsa) * 0.1;
                    break;
                case "ENERGIA":
                    exp += strutturaCosti.get(risorsa) * 0.5;
                    break;
                case "CIVILI":
                    exp += strutturaCosti.get(risorsa) * 1.5;
                    break;
                case "ACQUA":
                    exp += strutturaCosti.get(risorsa) * 10.9;
                    break;
            }
        }

        return exp;
    }

    public Double convertCostToExpSviluppoStrutture(Long strutturaId, String bearerToken) {

        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);

        Double exp = 0.0;

        Map<String, Double> strutturaCosti = getCostiSviluppoStruttura(strutturaId, playerId);

        for (String risorsa : strutturaCosti.keySet()) {
            switch (risorsa) {
                case "BITCOIN":
                    exp += strutturaCosti.get(risorsa) * 0.05;
                    break;
                case "MICROCHIP":
                    exp += strutturaCosti.get(risorsa) * 0.08;
                    break;
                case "METALLO":
                    exp += strutturaCosti.get(risorsa) * 0.1;
                    break;
                case "ENERGIA":
                    exp += strutturaCosti.get(risorsa) * 0.5;
                    break;
                case "CIVILI":
                    exp += strutturaCosti.get(risorsa) * 1.5;
                    break;
                case "ACQUA":
                    exp += strutturaCosti.get(risorsa) * 10.9;
                    break;
            }
        }

        return exp;
    }

    public Double convertCostToExpNew(Map<String, Double> costi) {

        double exp = 0.0;

        for (String risorsa : costi.keySet()) {
            switch (risorsa) {
                case "BITCOIN":
                    exp += costi.get(risorsa) * 0.05;
                    break;
                case "MICROCHIP":
                    exp += costi.get(risorsa) * 0.08;
                    break;
                case "METALLO":
                    exp += costi.get(risorsa) * 0.1;
                    break;
                case "ENERGIA":
                    exp += costi.get(risorsa) * 0.5;
                    break;
                case "CIVILI":
                    exp += costi.get(risorsa) * 1.5;
                    break;
                case "ACQUA":
                    exp += costi.get(risorsa) * 10.9;
                    break;
            }
        }

        return exp;
    }

    private String getJWTFromHeaderRequest(String authorizationToken) {
        if (StringUtils.hasText(authorizationToken) && authorizationToken.startsWith("Bearer ")) {
            return authorizationToken.substring(7);
        } else {
            return null;
        }
    }

    private Long getPlayerIdByAuthorizationToken(String authorizationToken) {
        String jwt = this.getJWTFromHeaderRequest(authorizationToken);
        String username = jwtGenerator.extractUsername(jwt);
        return userRepository.getPlayerIdByUsername(username);
    }
}






























