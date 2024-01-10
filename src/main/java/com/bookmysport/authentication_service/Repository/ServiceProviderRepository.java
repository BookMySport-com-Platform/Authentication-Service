package com.bookmysport.authentication_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmysport.authentication_service.Models.ServiceProviderModel;

public interface ServiceProviderRepository extends JpaRepository<ServiceProviderModel, UUID> {

}
