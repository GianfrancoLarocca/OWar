package com.giancotsu.owar.repository;

import com.giancotsu.owar.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query(value = """
            SELECT users.player_id
            FROM users
            WHERE username = :username
            """, nativeQuery = true)
    Long getPlayerIdByUsername(String username);
}
