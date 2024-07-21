package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.Soldato;
import com.giancotsu.owar.projection.BasicEsercitoInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SoldatoRepository extends JpaRepository<Soldato, Long> {

    Optional<Soldato> findSoldatoByPlayerIdAndDatiSoldatoId(Long playerId, Long soldatoId);

    @Query(value = """
            SELECT ds.id, s.quantita_soldati AS soldati, ds.nome, ds.url_immagine AS url
            FROM soldato AS s
            INNER JOIN dati_soldato AS ds
            ON ds.id = s.dati_soldato_id AND s.player_id = :playerId
            """, nativeQuery = true)
    List<BasicEsercitoInfoProjection> getAllEsercitoBasicInfo(Long playerId);
}
