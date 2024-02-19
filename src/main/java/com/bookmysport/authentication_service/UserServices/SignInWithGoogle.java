package com.bookmysport.authentication_service.UserServices;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import com.bookmysport.authentication_service.Models.UserModel;
import com.bookmysport.authentication_service.Repository.ServiceProviderRepository;
import com.bookmysport.authentication_service.Repository.UserRepository;

@Service
public class SignInWithGoogle {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private UserModel userModel;

    @Autowired
    private ServiceProviderModel serviceProviderModel;

    public void registrationByGoogleService(String role, Principal principal) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;
        OAuth2User userDetails = oauthToken.getPrincipal();

        // if (role.equals("user")) {
        //     userModel.setUserName(userDetails.getAttribute("name"));
        //     userModel.setEmail(userDetails.getAttribute("email"));
        //     userModel.setSignInWithGoogle(true);
        //     userRepository.save(userModel);
        //     return ResponseEntity.status(HttpStatus.OK).body("User added.");
        // }
        // else if (role.equals("serviceprovider")) {
        //     serviceProviderModel.setUserName(userDetails.getAttribute("name"));
        //     serviceProviderModel.setEmail(userDetails.getAttribute("email"));
        //     serviceProviderModel.setSignInWithGoogle(true);
        //     serviceProviderRepository.save(serviceProviderModel);
        //     return ResponseEntity.status(HttpStatus.OK).body("User added.");
        // }
    }
}
