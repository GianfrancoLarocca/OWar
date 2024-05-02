package com.giancotsu.owar.service.player;

import com.giancotsu.owar.entity.player.PlayerSviluppo;
import com.giancotsu.owar.projection.SviluppoCostiProjection;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.PlayerSviluppoRepository;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CostiService {

    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final RisorseService risorseService;
    private final UserRepository userRepository;
    private final JWTGenerator jwtGenerator;

    public CostiService(PlayerSviluppoRepository playerSviluppoRepository, RisorseService risorseService, UserRepository userRepository, JWTGenerator jwtGenerator) {
        this.playerSviluppoRepository = playerSviluppoRepository;
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

    public boolean pay(Long strutturaId, String bearerToken) {
        Long playerId = getPlayerIdByAuthorizationToken(bearerToken);
        risorseService.payment(getCostiStruttura(strutturaId, playerId), playerId);
        return false;
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






























