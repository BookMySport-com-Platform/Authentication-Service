package com.bookmysport.authentication_service.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookmysport.authentication_service.Models.AvatarModel;
import com.bookmysport.authentication_service.Models.LoginModel;
import com.bookmysport.authentication_service.Models.ResponseMessage;
import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import com.bookmysport.authentication_service.RatingService.PlaygroundRating;
import com.bookmysport.authentication_service.Repository.AvatarUploadRepository;
import com.bookmysport.authentication_service.Repository.ServiceProviderRepository;
import com.bookmysport.authentication_service.SearchFunction.SearchByAddressAndCentreName;
import com.bookmysport.authentication_service.Services.AvatarUploadService;
import com.bookmysport.authentication_service.Services.FetchAvatars;
import com.bookmysport.authentication_service.UserServices.ArenaDetailsUpdateService;
import com.bookmysport.authentication_service.UserServices.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
@CrossOrigin(origins="http://localhost:5173")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArenaDetailsUpdateService arenaDetailsUpdateService;

    @Autowired
    private SearchByAddressAndCentreName searchByAddressAndCentreName;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private PlaygroundRating playgroundRating;

    @Autowired
    private AvatarUploadService avatarUploadService;

    @Autowired
    private FetchAvatars fetchAvatars;

    @Autowired
    private AvatarUploadRepository avatarUploadRepository;

    @PostMapping("adduser")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Object userOrService, BindingResult bindingResult,
            @RequestHeader String role) {
        return userService.userRegisterService(userOrService, bindingResult, role);
    }

    @GetMapping("getuserdetailsbytoken")
    public ResponseEntity<Object> getUserDetailsByToken(@RequestHeader String token, @RequestHeader String role) {
        return userService.getUserDetailsByEmailService(token, role);
    }

    @PostMapping("login")
    public ResponseEntity<Object> verifyUser(@RequestBody LoginModel loginModel, @RequestHeader String role) {
        return userService.userLoginService(loginModel, role);
    }

    @PostMapping("2fa")
    public ResponseEntity<Object> twofa(@RequestHeader int otpforTwoFAFromUser, @RequestHeader String email,
            @RequestHeader String role) {
        return userService.TwoFAService(otpforTwoFAFromUser, email, role);
    }

    @PostMapping("forgotpassword")
    public ResponseEntity<Object> forgotPassword(@RequestHeader String email, @RequestHeader String role) {
        return userService.forgotPasswordService(email, role);
    }

    @PostMapping("verifyOtpforforgotpassword")
    public ResponseEntity<Object> verifyTheUserOtp(@RequestHeader int otp, @RequestHeader String email) {
        return userService.verifyTheOtpEnteredByUser(otp, email);
    }

    @PostMapping("resetpassword")
    public ResponseEntity<Object> resetThePassword(@RequestHeader String passwordFromUser, @RequestHeader String role,
            @RequestHeader String email) {
        return userService.resetThePasswordService(passwordFromUser, role, email);
    }

    @PutMapping("updateplaygrounddetails")
    public ResponseEntity<ResponseMessage> updateArenaDetails(@RequestHeader String token,
            @RequestBody ServiceProviderModel latestDetails) {
        return arenaDetailsUpdateService.playGroundDetailsUpdateService(token, latestDetails);
    }

    @GetMapping("searchbyaddressandcentrename")
    public List<ServiceProviderModel> searchFunction(@RequestHeader String searchItem) {
        return searchByAddressAndCentreName.searchByAddressAndCentreNameService(searchItem);
    }

    @GetMapping("getdetailsbyspid")
    public Optional<ServiceProviderModel> getDetailsBySpId(@RequestHeader String spId) {
        return serviceProviderRepository.findById(UUID.fromString(spId));
    }

    @PostMapping("addrating")
    public ResponseEntity<ResponseMessage> rating(@RequestHeader String spId, @RequestHeader float rating) {
        return playgroundRating.playgroundRatingService(spId, rating);
    }

    @PostMapping("uploadavatar")
    public ResponseEntity<ResponseMessage> avatarUpload(@RequestHeader String token, @RequestHeader String role,
            @RequestParam("avatar") MultipartFile avatar) {
        return avatarUploadService.avatarUploadService(token, role, avatar);
    }

    @GetMapping("getavatar")
    public ResponseEntity<Map<String, Object>> getAvatar(@RequestHeader String token, @RequestHeader String role) {
        return fetchAvatars.fetchAvatarService(token, role);
    }

    @GetMapping("getallarenas")
    public List<ServiceProviderModel> getAllArenas()
    {
        return serviceProviderRepository.findAll();
    }

    @GetMapping("getavatarbyid")
    public AvatarModel getAvatarById(@RequestHeader String userId)
    {
        return avatarUploadRepository.findByUserId(UUID.fromString(userId));
    }

}
