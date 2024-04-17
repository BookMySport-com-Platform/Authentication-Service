package com.bookmysport.authentication_service.Services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.bookmysport.authentication_service.Models.AvatarModel;
import com.bookmysport.authentication_service.Models.ResponseMessage;
import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import com.bookmysport.authentication_service.Models.UserModel;
import com.bookmysport.authentication_service.Repository.AvatarUploadRepository;
import com.bookmysport.authentication_service.Repository.ServiceProviderRepository;
import com.bookmysport.authentication_service.Repository.UserRepository;
import com.bookmysport.authentication_service.UserServices.AuthService;

@Service
public class AvatarUploadService {

    @Autowired
    private AvatarUploadRepository avatarUploadRepository;

    @Autowired
    private S3PutObjectService s3PutObjectService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> avatarUploadService(String token, String role, MultipartFile avatar) {
        try {
            String email = authService.verifyToken(token);
            if (role.equals("user")) {
                UserModel user = userRepository.findByEmail(email);
                if (user != null) {

                    UUID keyForAvatar = UUID.randomUUID();
                    ResponseMessage responseAfterAvatarUpload = s3PutObjectService
                            .putObjectService(user.getId().toString(), keyForAvatar.toString(), avatar).getBody();

                    if (responseAfterAvatarUpload != null) {
                        AvatarModel avatarModel = new AvatarModel();

                        avatarModel.setUserId(user.getId());
                        avatarModel.setAvatarUrl(responseAfterAvatarUpload.getMessage());

                        avatarUploadRepository.save(avatarModel);

                        responseMessage.setSuccess(true);
                        responseMessage.setMessage("Avatar upload successful.");
                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                    } else {
                        responseMessage.setSuccess(false);
                        responseMessage.setMessage("Error occured in avatarUploadService method");
                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                    }

                } else {
                    responseMessage.setSuccess(false);
                    responseMessage.setMessage("Invalid email");
                    return ResponseEntity.ok().body(responseMessage);
                }
            } else {
                ServiceProviderModel serviceProvider = serviceProviderRepository.findByEmail(email);
                if (serviceProvider != null) {
                    UUID keyForAvatar = UUID.randomUUID();
                    ResponseMessage responseAfterAvatarUpload = s3PutObjectService
                            .putObjectService(serviceProvider.getId().toString(), keyForAvatar.toString(), avatar)
                            .getBody();

                    if (responseAfterAvatarUpload != null) {
                        AvatarModel avatarModel = new AvatarModel();

                        avatarModel.setUserId(serviceProvider.getId());
                        avatarModel.setAvatarUrl(responseAfterAvatarUpload.getMessage());

                        avatarUploadRepository.save(avatarModel);

                        responseMessage.setSuccess(true);
                        responseMessage.setMessage("Avatar upload successful.");
                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                    } else {
                        responseMessage.setSuccess(false);
                        responseMessage.setMessage("Error occured in avatarUploadService method");
                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                    }
                } else {
                    responseMessage.setSuccess(false);
                    responseMessage.setMessage("Invalid email");
                    return ResponseEntity.ok().body(responseMessage);
                }
            }
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
