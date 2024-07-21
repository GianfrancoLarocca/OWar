package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.DatiSoldato;
import com.giancotsu.owar.projection.SoldatoCostiProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DatiSoldatoRepository extends JpaRepository<DatiSoldato, Long> {

    Optional<DatiSoldato> findByNome(String name);

    @Query(value = """
            SELECT costi.costo_base_esercito_key AS risorsa, costi.costo_base_esercito AS costo
            FROM dati_soldato AS ds
            INNER JOIN map_costo_base_soldati AS costi
            ON ds.id = costi.soldato_id AND ds.id = :soldatoId
            INNER JOIN soldato
            ON soldato.dati_soldato_id = ds.id AND soldato.player_id = :playerId
            """, nativeQuery = true)
    List<SoldatoCostiProjection> getCosti(Long playerId, Long soldatoId);
}
