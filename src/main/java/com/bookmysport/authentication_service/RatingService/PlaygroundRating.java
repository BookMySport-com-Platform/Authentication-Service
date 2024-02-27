package com.bookmysport.authentication_service.RatingService;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.authentication_service.Models.ResponseMessage;
import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import com.bookmysport.authentication_service.Repository.ServiceProviderRepository;

@Service
public class PlaygroundRating {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> playgroundRatingService(String spId, float rating) {
        try {
            if (rating > 5 || rating < 0) {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Rating cannot exceed 5 and cannot be less than 0");
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(responseMessage);
            } else {
                Optional<ServiceProviderModel> serviceProviderOptional = serviceProviderRepository
                        .findById(UUID.fromString(spId));

                if (serviceProviderOptional.isPresent()) {
                    ServiceProviderModel serviceProvider = serviceProviderOptional.get();

                    if (serviceProvider.getNumberOfRatings() == 0) {
                        serviceProvider.setRating(Math.round(rating * 10.0) / 10.0);
                        serviceProvider.setRatingSum(rating);
                        serviceProvider.setNumberOfRatings(1);
                    } else {
                        int totalNumberOfRatings = serviceProvider.getNumberOfRatings() + 1;
                        serviceProvider.setRatingSum(serviceProvider.getRatingSum() + rating);
                        serviceProvider.setNumberOfRatings(totalNumberOfRatings);
                        serviceProvider.setRating(Math.round((serviceProvider.getRatingSum() / totalNumberOfRatings) * 10.0) / 10.0);
                    }

                    serviceProviderRepository.save(serviceProvider);

                    responseMessage.setSuccess(true);
                    responseMessage
                            .setMessage(
                                    "Rating added and number of ratings are: " + serviceProvider.getNumberOfRatings());
                } else {
                    responseMessage.setSuccess(false);
                    responseMessage.setMessage("Invalid SpID");
                    return ResponseEntity.badRequest().body(responseMessage);
                }

                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage(
                    "Internal Server Error in PlaygroundRating.java. Method: playgroundRatingService. Reason: "
                            + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);

        }

    }
}
