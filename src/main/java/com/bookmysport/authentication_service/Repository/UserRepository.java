package com.bookmysport.authentication_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmysport.authentication_service.Models.UserModel;

public interface UserRepository extends JpaRepository<UserModel,UUID> {
    UserModel findByEmail(String email);
}
