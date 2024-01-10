package com.bookmysport.authentication_service.Repository;

import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmysport.authentication_service.Models.UserModel;

public interface UserRepository extends JpaRepository<UserModel,UUID> {
    UserModel findByEmail(String email);
}
