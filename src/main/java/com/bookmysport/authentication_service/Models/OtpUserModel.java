package com.bookmysport.authentication_service.Models;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OtpUserModel {
    private String email;
    private int otp;
}
