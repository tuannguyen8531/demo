package com.dev.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.server.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    @Query("SELECT a FROM Admin a WHERE a.username = :username AND a.deletedFlg = false")
    Optional<Admin> findByUsername(String username);

    @Query("SELECT a FROM Admin a WHERE a.email = :email AND a.deletedFlg = false")
    Optional<Admin> findByEmail(String email);

    @Query("SELECT a FROM Admin a WHERE a.refreshToken = :refreshToken AND a.deletedFlg = false AND a.refreshTokenExpiration > CURRENT_TIMESTAMP")
    Optional<Admin> findByRefreshToken(String refreshToken);
}
