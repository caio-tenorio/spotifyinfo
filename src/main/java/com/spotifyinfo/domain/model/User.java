package com.spotifyinfo.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "users")
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String email;
    private String username;
    @Column(name = "encrypted_password")
    private String encryptedPassword;
}
