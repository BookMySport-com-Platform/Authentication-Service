package com.bookmysport.authentication_service.Services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.authentication_service.Models.AvatarModel;
import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import com.bookmysport.authentication_service.Models.UserModel;
import com.bookmysport.authentication_service.Repository.AvatarUploadRepository;
import com.bookmysport.authentication_service.Repository.ServiceProviderRepository;
import com.bookmysport.authentication_service.Repository.UserRepository;
import com.bookmysport.authentication_service.UserServices.AuthService;

@Service
public class FetchAvatars {

    @Autowired
    private AvatarUploadRepository avatarUploadRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    public ResponseEntity<Map<String, Object>> fetchAvatarService(String token, String role) {

        try {
            String email = authService.verifyToken(token);
            if (role.equals("user")) {
                UserModel user = userRepository.findByEmail(email);
                System.out.println(user);
                if (user != null) {
                    Map<String, Object> response = new HashMap<>();
                    AvatarModel avatar = avatarUploadRepository.findByUserId(user.getId());

                    response.put("success", true);
                    response.put("avatar", avatar.getAvatarUrl());
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("avatar", "Invalid Email");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } else {
                ServiceProviderModel serviceProvider = serviceProviderRepository.findByEmail(email);
                if (serviceProvider != null) {
                    Map<String, Object> response = new HashMap<>();

                    AvatarModel avatar = avatarUploadRepository.findByUserId(serviceProvider.getId());

                    response.put("success", true);
                    response.put("avatar", avatar);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("avatar", "Invalid Email");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("avatar", "Internal Server Error in FetchAvatars. Reason: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
