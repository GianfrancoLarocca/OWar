package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.dto.ClassificaDto;
import com.giancotsu.owar.entity.player.PlayerEntity;
import com.giancotsu.owar.projection.ClassificaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassificaRepository extends JpaRepository<PlayerEntity, Long> {

    @Query(value = """
            SELECT player.id, bi.nickname, bi.lvl AS livello, bi.cp, bi.url_immagine AS img
            FROM player
            INNER JOIN basic_information AS bi
            ON player.basic_information_id = bi.id
            ORDER BY bi.cp desc
            """, nativeQuery = true)
    List<ClassificaProjection> getClassificaCp();



    @Query(value = """
            SELECT player.id, bi.nickname, bi.lvl AS livello, bi.cp, bi.url_immagine AS img
            FROM player
            INNER JOIN basic_information AS bi
            ON player.basic_information_id = bi.id
            ORDER BY bi.lvl desc
            """, nativeQuery = true)
    List<ClassificaProjection> getClassificaLvl();
}
