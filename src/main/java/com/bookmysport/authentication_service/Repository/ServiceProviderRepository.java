package com.bookmysport.authentication_service.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import jakarta.transaction.Transactional;

public interface ServiceProviderRepository extends JpaRepository<ServiceProviderModel, UUID> {
    ServiceProviderModel findByEmail(String email);

    @Transactional
    @Query(value = "SELECT * FROM service_provider_details WHERE address like %?1% or centre_name like %?1%", nativeQuery = true)
    List<ServiceProviderModel> findByAddressAndCentreName(String search);

    @SuppressWarnings("null")
    Optional<ServiceProviderModel> findById(UUID id);
}
