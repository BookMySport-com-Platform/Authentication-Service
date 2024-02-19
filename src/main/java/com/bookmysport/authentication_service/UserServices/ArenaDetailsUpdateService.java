package com.bookmysport.authentication_service.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.authentication_service.Models.ResponseMessage;
import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import com.bookmysport.authentication_service.Repository.ServiceProviderRepository;

@Service
public class ArenaDetailsUpdateService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> playGroundDetailsUpdateService(String token, ServiceProviderModel latestDetails) {
        try {
            String email = authService.verifyToken(token);
            System.out.println(email);
            ServiceProviderModel updateToBeDone = serviceProviderRepository.findByEmail(email);
            System.out.println(updateToBeDone);

            if (updateToBeDone == null) {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Service provider with this email: " + email + " doesn't exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            } else {
                updateToBeDone.setCentreName(latestDetails.getCentreName());
                updateToBeDone.setAddress(latestDetails.getAddress());
                updateToBeDone.setPhoneNumber(latestDetails.getPhoneNumber());
                updateToBeDone.setStartTime(latestDetails.getStartTime());
                updateToBeDone.setStopTime(latestDetails.getStopTime());
                serviceProviderRepository.save(updateToBeDone);

                responseMessage.setSuccess(true);
                responseMessage.setMessage("Details updated successfully");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage(
                    "Internal server error in ArenaDetailsUpdateService.java. Method: arenaDetailsUpdateService. Reason: "
                            + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
