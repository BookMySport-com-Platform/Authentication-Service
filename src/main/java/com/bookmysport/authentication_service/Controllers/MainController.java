package com.bookmysport.authentication_service.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmysport.authentication_service.Models.LoginModel;
import com.bookmysport.authentication_service.Models.ResponseMessage;
import com.bookmysport.authentication_service.Models.ServiceProviderModel;
import com.bookmysport.authentication_service.UserServices.ArenaDetailsUpdateService;
import com.bookmysport.authentication_service.UserServices.SignInWithGoogle;
import com.bookmysport.authentication_service.UserServices.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArenaDetailsUpdateService arenaDetailsUpdateService;

    @Autowired
    private SignInWithGoogle signInWithGoogle;

    @PostMapping("adduser")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Object userOrService, BindingResult bindingResult,
            @RequestHeader String role) {
        return userService.userRegisterService(userOrService, bindingResult, role);
    }

    @GetMapping("getuserdetailsbytoken")
    public ResponseEntity<Object> getUserDetailsByToken(@RequestHeader String token, @RequestHeader String role) {
        return userService.getUserDetailsByEmailService(token, role);
    }

    @GetMapping("login")
    public ResponseEntity<Object> verifyUser(@RequestBody LoginModel loginModel, @RequestHeader String role) {
        return userService.userLoginService(loginModel, role);
    }

    @PostMapping("2fa")
    public ResponseEntity<Object> twofa(@RequestHeader int otpforTwoFAFromUser, @RequestHeader String role) {
        return userService.TwoFAService(otpforTwoFAFromUser, role);
    }

    @PostMapping("forgotpassword")
    public ResponseEntity<Object> forgotPassword(@RequestHeader String email) {
        return userService.forgotPasswordService(email);
    }

    @PostMapping("verifyOtpforforgotpassword")
    public ResponseEntity<Object> verifyTheUserOtp(@RequestHeader String otp) {
        return userService.verifyTheOtpEnteredByUser(otp);
    }

    @PostMapping("resetpassword")
    public ResponseEntity<Object> resetThePassword(@RequestHeader String passwordFromUser, @RequestHeader String role) {
        return userService.resetThePasswordService(passwordFromUser, role);
    }

    @PutMapping("updateplaygrounddetails")
    public ResponseEntity<ResponseMessage> updateArenaDetails(@RequestHeader String token,
            @RequestBody ServiceProviderModel latestDetails) {
        return arenaDetailsUpdateService.playGroundDetailsUpdateService(token, latestDetails);
    }

    // @GetMapping("loginwithgoogle")
    // public ResponseEntity<String> loginWithGoogle(String role, Principal principal) {
    //     return signInWithGoogle.registrationByGoogleService(role, principal);
    // }

    @GetMapping()
    public String welcome() {
        return "Welcome to Google";
    }

}
