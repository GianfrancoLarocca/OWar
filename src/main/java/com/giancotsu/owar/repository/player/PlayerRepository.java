package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    @Query(value = """
            SELECT player.id
            FROM player
            """, nativeQuery = true)
    Set<Long> getAllPlayerId();

    @Query(value = """
            SELECT p.id, p.is_online, p.is_banned, p.is_vacationed, p.basic_information_id, p.registro_attivita_id, p.player_risorse_id, p.contatori_id, p.scelta_richiesta_amici
            FROM player AS p
            INNER JOIN basic_information AS bi
            ON bi.id = p.basic_information_id AND bi.nickname LIKE %:nickname%
            """, nativeQuery = true)
    List<PlayerEntity> findPlayerByNickname(@Param("nickname") String nickname);

    @Query(value = """
            SELECT player_friends.friend_id
            FROM player_friends
            WHERE player_friends.player_id = :myId
            """, nativeQuery = true)
    List<Long> friendsIds(@Param("myId") long myId);



}
