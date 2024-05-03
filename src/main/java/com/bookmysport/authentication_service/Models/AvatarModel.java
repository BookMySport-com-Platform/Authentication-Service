package com.bookmysport.authentication_service.Models;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Component
@Table(name = "avatar_db")
public class AvatarModel {

    @Id
    private UUID avatarId;

    @Column(unique = true)
    private UUID userId;

    @Column(length = 400)
    private String avatarUrl;

    private LocalDate dateOfGenration;
}
