package com.bookmysport.authentication_service.SearchFunction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import com.bookmysport.authentication_service.Repository.ServiceProviderRepository;

@Service
public class SearchByAddressAndCentreName {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    public List<ServiceProviderModel> searchByAddressAndCentreNameService(String searchItem) {
        List<ServiceProviderModel> searchResults = serviceProviderRepository.findByAddressAndCentreName(searchItem);
        return searchResults;
    }
}
