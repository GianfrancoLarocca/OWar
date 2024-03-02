package com.giancotsu.owar.repository;

import com.giancotsu.owar.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
