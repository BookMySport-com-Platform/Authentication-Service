package com.bookmysport.authentication_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmysport.authentication_service.Models.AvatarModel;

public interface AvatarUploadRepository extends JpaRepository<AvatarModel, UUID> {
    AvatarModel findByUserId(UUID userId);
}
