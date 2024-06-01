package com.giancotsu.owar.repository.player;

import com.giancotsu.owar.entity.player.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query(value = """
            SELECT *
            FROM friend_request
            WHERE friend_request.sender = :senderId AND friend_request.receiver = :receiverId
            """, nativeQuery = true)
    Optional<List<FriendRequest>> alreadyExists(@Param("senderId") long senderId, @Param("receiverId") long receiverId);

    @Query(value = """
            SELECT friend_request.receiver
            FROM friend_request
            WHERE friend_request.sender = :myId
            """, nativeQuery = true)
    List<Long> getSentFriendRequest(@Param("myId") long myId);

    @Query(value = """
            SELECT friend_request.sender
            FROM friend_request
            WHERE friend_request.receiver = :myId
            """, nativeQuery = true)
    List<Long> getReceivedFriendRequest(@Param("myId") long myId);
}
