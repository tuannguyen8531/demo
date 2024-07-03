package com.dev.server.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dev.server.util.Constants;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "admins")
@EntityListeners(AuditingEntityListener.class)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private String role = Constants.ROLE_STAFF;

    @Column(name = "role_menu", nullable = true)
    private String roleMenu;

    @Column(name = "deleted_flg", nullable = false)
    private boolean deletedFlg = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Admin(String fullName, String username, String password, String email) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
