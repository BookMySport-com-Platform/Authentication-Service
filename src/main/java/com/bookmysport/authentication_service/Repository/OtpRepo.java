package com.bookmysport.authentication_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmysport.authentication_service.Models.OTPModel;
import java.util.List;
import java.time.LocalDateTime;


public interface OtpRepo extends JpaRepository<OTPModel,UUID>{

    OTPModel findByEmail(String email);

    List<OTPModel> findByCreatedAt(LocalDateTime createdAt);
    
}
