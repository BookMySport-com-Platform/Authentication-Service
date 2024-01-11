package com.bookmysport.authentication_service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmysport.authentication_service.Models.LoginModel;
import com.bookmysport.authentication_service.UserServices.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private UserService userService;

    @PostMapping("adduser")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Object userOrService, BindingResult bindingResult,@RequestHeader int l) {
        return userService.userRegisterService(userOrService, bindingResult,l);
    }

    @GetMapping("login")
    public ResponseEntity<Object> verifyUser(@RequestBody LoginModel loginModel) {
        return userService.userLoginService(loginModel);
    }

    @PostMapping("2fa")
    public ResponseEntity<Object> twofa(@RequestHeader int otpforTwoFAFromUser) {
        return userService.TwoFAService(otpforTwoFAFromUser);
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
    public ResponseEntity<Object> resetThePassword(@RequestHeader String passwordFromUser) {
        return userService.resetThePasswordService(passwordFromUser);
    }

}
